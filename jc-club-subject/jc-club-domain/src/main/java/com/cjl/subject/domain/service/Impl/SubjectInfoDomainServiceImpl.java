package com.cjl.subject.domain.service.Impl;

import com.alibaba.fastjson.JSON;
import com.cjl.subject.common.entity.PageResult;
import com.cjl.subject.common.enums.IsDeletedFlagEnum;
import com.cjl.subject.domain.convert.SubjectInfoConverter;
import com.cjl.subject.domain.entity.SubjectInfoBO;
import com.cjl.subject.domain.handler.subject.SubjectTypeHandler;
import com.cjl.subject.domain.handler.subject.SubjectTypeHandlerFactory;
import com.cjl.subject.domain.service.SubjectInfoDomainService;
import com.cjl.subject.infra.basic.entity.SubjectInfo;
import com.cjl.subject.infra.basic.entity.SubjectMapping;
import com.cjl.subject.infra.basic.service.SubjectInfoService;
import com.cjl.subject.infra.basic.service.SubjectMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 添加试题
     *
     * @param subjectInfoBO
     * @return
     */
    @Override
    public void add(SubjectInfoBO subjectInfoBO) {
        //先插入subject_info表
        SubjectInfo subjectInfo = SubjectInfoConverter.INSTANCE.convertBoToEntity(subjectInfoBO);
        subjectInfo.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        subjectInfoService.insert(subjectInfo);
        log.info("subjectInfo:{}", JSON.toJSONString(subjectInfo));
        log.info("subjectInfoBO:{}", JSON.toJSONString(subjectInfoBO));

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
        SubjectInfo subjectInfo = SubjectInfoConverter.INSTANCE.convertBoToEntity(subjectInfoBO);
        Integer count = subjectInfoService.countByCondition(subjectInfo, subjectInfoBO.getCategoryId(), subjectInfoBO.getLabelId());
        if (count == 0) {
            return pageResult;
        }
        List<SubjectInfo> subjectInfoList =subjectInfoService.queryPage(subjectInfo, subjectInfoBO.getCategoryId(),
                subjectInfoBO.getLabelId(), start, subjectInfoBO.getPageSize());
        List<SubjectInfoBO> subjectInfoBOList = SubjectInfoConverter.INSTANCE.convertListEntityToBo(subjectInfoList);
        pageResult.setRecords(subjectInfoBOList);
        pageResult.setTotal(count);
        return pageResult;
    }
}
