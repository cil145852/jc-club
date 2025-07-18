package com.cjl.subject.domain.service.Impl;

import com.alibaba.fastjson.JSON;
import com.cjl.subject.common.entity.PageResult;
import com.cjl.subject.common.enums.IsDeletedFlagEnum;
import com.cjl.subject.common.util.IdWorkerUtil;
import com.cjl.subject.common.util.LoginUtil;
import com.cjl.subject.domain.convert.SubjectInfoConverter;
import com.cjl.subject.domain.entity.SubjectInfoBO;
import com.cjl.subject.domain.entity.SubjectTypeBO;
import com.cjl.subject.domain.handler.subject.SubjectTypeHandler;
import com.cjl.subject.domain.handler.subject.SubjectTypeHandlerFactory;
import com.cjl.subject.domain.redis.RedisUtil;
import com.cjl.subject.domain.service.SubjectInfoDomainService;
import com.cjl.subject.domain.service.SubjectLikedDomainService;
import com.cjl.subject.infra.basic.entity.SubjectInfo;
import com.cjl.subject.infra.basic.entity.SubjectInfoEs;
import com.cjl.subject.infra.basic.entity.SubjectLabel;
import com.cjl.subject.infra.basic.entity.SubjectMapping;
import com.cjl.subject.infra.basic.service.SubjectEsService;
import com.cjl.subject.infra.basic.service.SubjectInfoService;
import com.cjl.subject.infra.basic.service.SubjectLabelService;
import com.cjl.subject.infra.basic.service.SubjectMappingService;
import com.cjl.subject.infra.entity.UserInfo;
import com.cjl.subject.infra.rpc.UserRPC;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-04-9:05
 * @Description
 */
@Service
@Slf4j
public class SubjectInfoDomainServiceImpl implements SubjectInfoDomainService {
    @Resource
    private SubjectInfoService subjectInfoService;

    @Resource
    private SubjectTypeHandlerFactory subjectTypeHandlerFactory;

    @Resource
    private SubjectMappingService subjectMappingService;

    @Resource
    private SubjectLabelService subjectLabelService;

    @Resource
    private SubjectEsService subjectEsService;

    @Resource
    private SubjectLikedDomainService subjectLikedDomainService;

    @Resource
    private RedisUtil redisUtil;

    private static final String RANK_KEY = "subject_rank";

    @Resource
    private UserRPC userRPC;

    /**
     * 添加试题
     *
     * @param subjectInfoBO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SubjectInfoBO subjectInfoBO) {
        //先插入subject_info表
        SubjectInfo subjectInfo = SubjectInfoConverter.INSTANCE.convertBOToEntity(subjectInfoBO);
        subjectInfoService.insert(subjectInfo);

        subjectInfoBO.setId(subjectInfo.getId());
        //再根据题目类型如:单选题、多选题、判断题、填空题、简答题插入对应的表
        SubjectTypeHandler subjectTypeHandler = subjectTypeHandlerFactory.getSubjectTypeHandler(subjectInfoBO.getSubjectType());
        subjectTypeHandler.add(subjectInfoBO);
        //最后插入题目分类关系表
        List<SubjectMapping> subjectMappingList = new ArrayList<>();
        List<Long> labelIds = subjectInfoBO.getLabelIds();
        Long subjectId = subjectInfo.getId();
        subjectInfoBO.getCategoryIds().forEach(categoryId -> {
            labelIds.forEach(labelId -> {
                SubjectMapping subjectMapping = new SubjectMapping();
                subjectMapping.setSubjectId(subjectId);
                subjectMapping.setCategoryId(categoryId);
                subjectMapping.setLabelId(labelId);
                subjectMapping.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
                subjectMappingList.add(subjectMapping);
            });
        });
        subjectMappingService.batchInsert(subjectMappingList);

        //插入题目后将题目信息同步到es
        subjectEsService.insert(convert2SubjectInfoEs(subjectInfoBO));

        //修改排行榜数据
        redisUtil.addScore(RANK_KEY, LoginUtil.getLoginId(), 1);
    }

    private SubjectInfoEs convert2SubjectInfoEs(SubjectInfoBO subjectInfoBO) {
        SubjectInfoEs subjectInfoEs = new SubjectInfoEs();
        subjectInfoEs.setDocId(new IdWorkerUtil(1, 1, 1).nextId());
        subjectInfoEs.setSubjectId(subjectInfoBO.getId());
        subjectInfoEs.setSubjectName(subjectInfoBO.getSubjectName());
        subjectInfoEs.setSubjectAnswer(subjectInfoBO.getSubjectAnswer());
        subjectInfoEs.setSubjectType(subjectInfoBO.getSubjectType());
        subjectInfoEs.setCreateTime(new Date().getTime());
        subjectInfoEs.setCreateUser("cjl");
        return subjectInfoEs;
    }

    /**
     * 分页查询试题
     *
     * @param subjectInfoBO
     * @return
     */
    @Override
    public PageResult<SubjectInfoBO> getSubjectPage(SubjectInfoBO subjectInfoBO) {
        PageResult<SubjectInfoBO> pageResult = new PageResult<>();
        pageResult.setPageNo(subjectInfoBO.getPageNo());
        pageResult.setPageSize(subjectInfoBO.getPageSize());
        Integer start = (subjectInfoBO.getPageNo() - 1) * subjectInfoBO.getPageSize();
        SubjectInfo subjectInfo = SubjectInfoConverter.INSTANCE.convertBOToEntity(subjectInfoBO);
        Integer count = subjectInfoService.countByCondition(subjectInfo, subjectInfoBO.getCategoryId(), subjectInfoBO.getLabelId());
        if (count == 0) {
            return pageResult;
        }
        List<SubjectInfo> subjectInfoList = subjectInfoService.queryPage(subjectInfo, subjectInfoBO.getCategoryId(),
                subjectInfoBO.getLabelId(), start, subjectInfoBO.getPageSize());
        List<SubjectInfoBO> subjectInfoBOList = SubjectInfoConverter.INSTANCE.convertListEntityToBO(subjectInfoList);
        pageResult.setRecords(subjectInfoBOList);
        pageResult.setTotal(count);
        return pageResult;
    }

    /**
     * 根据条件查询题目信息
     *
     * @param subjectInfoBO
     * @return
     */
    @Override
    public SubjectInfoBO querySubjectInfo(SubjectInfoBO subjectInfoBO) {
        SubjectInfo subjectInfo = SubjectInfoConverter.INSTANCE.convertBOToEntity(subjectInfoBO);
        subjectInfo = subjectInfoService.queryById(subjectInfo.getId());
        SubjectTypeHandler subjectTypeHandler = subjectTypeHandlerFactory.getSubjectTypeHandler(subjectInfo.getSubjectType());
        SubjectTypeBO subjectTypeBO = subjectTypeHandler.query(subjectInfo.getId());
        subjectInfoBO = SubjectInfoConverter.INSTANCE.convertEntityToBO(subjectInfo);
        subjectInfoBO.setOptionList(subjectTypeBO.getOptionList());
        subjectInfoBO.setSubjectAnswer(subjectTypeBO.getSubjectAnswer());
        SubjectMapping subjectMapping = new SubjectMapping();
        subjectMapping.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        subjectMapping.setSubjectId(subjectInfo.getId());
        List<SubjectMapping> subjectMappingList = subjectMappingService.queryDistinctLabelId(subjectMapping);
        List<Long> labelIds = subjectMappingList
                .stream()
                .map(SubjectMapping::getLabelId)
                .collect(Collectors.toList());
        List<String> labelName = subjectLabelService.batchQueryByIds(labelIds)
                .stream()
                .map(SubjectLabel::getLabelName)
                .collect(Collectors.toList());
        subjectInfoBO.setLabelName(labelName);

        //设置点赞相关属性
        subjectInfoBO.setLiked(subjectLikedDomainService.isLiked(subjectInfoBO.getId(), LoginUtil.getLoginId()));
        subjectInfoBO.setSubjectCount(subjectLikedDomainService.getLikedCount(subjectInfoBO.getId()));
        return subjectInfoBO;
    }

    @Override
    public PageResult<SubjectInfoBO> getSubjectPageBySearch(SubjectInfoBO subjectInfoBO) {
        SubjectInfoEs req = SubjectInfoConverter.INSTANCE.convertBOToEsEntity(subjectInfoBO);
        PageResult<SubjectInfoEs> pageResult = subjectEsService.querySubjectList(req);
        PageResult<SubjectInfoBO> boPageResult = new PageResult<>();
        boPageResult.setRecords(SubjectInfoConverter.INSTANCE.convertListEsEntityToBO(pageResult.getResult()));
        return boPageResult;
    }

    @Override
    public List<SubjectInfoBO> getContributeList() {
        Set<ZSetOperations.TypedTuple<String>> typedTuples = redisUtil.rankWithScore(RANK_KEY, 0, 10);
        if (log.isInfoEnabled()) {
            log.info("SubjectInfoDomainServiceImpl.getContributeList.typedTuples:{}", JSON.toJSONString(typedTuples));
        }
        if (CollectionUtils.isEmpty(typedTuples)) {
            return Collections.emptyList();
        }
        return typedTuples.stream()
                .map(rank -> {
                    SubjectInfoBO subjectInfoBO = new SubjectInfoBO();
                    subjectInfoBO.setSubjectCount(rank.getScore().intValue());
                    String username = rank.getValue();
                    UserInfo userInfo = userRPC.getUserInfo(username);
                    subjectInfoBO.setCreateUser(userInfo.getNickname());
                    subjectInfoBO.setCreateUserAvatar(userInfo.getAvatar());
                    return subjectInfoBO;
                })
                .collect(Collectors.toList());
    }
}
