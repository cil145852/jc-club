package com.cjl.subject.domain.service.Impl;

import com.alibaba.fastjson.JSON;
import com.cjl.subject.common.enums.IsDeletedFlagEnum;
import com.cjl.subject.domain.convert.SubjectLabelConverter;
import com.cjl.subject.domain.entity.SubjectLabelBO;
import com.cjl.subject.domain.service.SubjectLabelDomainService;
import com.cjl.subject.infra.basic.entity.SubjectLabel;
import com.cjl.subject.infra.basic.entity.SubjectMapping;
import com.cjl.subject.infra.basic.service.SubjectLabelService;
import com.cjl.subject.infra.basic.service.SubjectMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-03-17:07
 * @Description
 */
@Service
@Slf4j
public class SubjectLabelDomainServiceImpl implements SubjectLabelDomainService {
    @Resource
    private SubjectLabelService subjectLabelService;

    @Resource
    private SubjectMappingService subjectMappingService;

    /**
     * 添加标签
     *
     * @param subjectLabelBO
     * @return
     */
    @Override
    public Boolean add(SubjectLabelBO subjectLabelBO) {
        SubjectLabel subjectLabel = SubjectLabelConverter.INSTANCE.convertBoToLabel(subjectLabelBO);
        subjectLabel.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        Integer count = subjectLabelService.insert(subjectLabel);
        return count > 0;
    }

    /**
     * 修改标签
     *
     * @param subjectLabelBO
     * @return
     */
    @Override
    public Boolean update(SubjectLabelBO subjectLabelBO) {
        SubjectLabel subjectLabel = SubjectLabelConverter.INSTANCE.convertBoToLabel(subjectLabelBO);
        Integer count = subjectLabelService.update(subjectLabel);
        return count > 0;
    }

    /**
     * 删除标签
     *
     * @param subjectLabelBO
     * @return
     */
    @Override
    public Boolean delete(SubjectLabelBO subjectLabelBO) {
        SubjectLabel subjectLabel = SubjectLabelConverter.INSTANCE.convertBoToLabel(subjectLabelBO);
        subjectLabel.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        Integer count = subjectLabelService.update(subjectLabel);
        return count > 0;
    }

    /**
     * 查询分类下的标签
     *
     * @param subjectLabelBO
     * @return
     */
    @Override
    public List<SubjectLabelBO> queryLabelByCategoryId(SubjectLabelBO subjectLabelBO) {
        Long categoryId = subjectLabelBO.getCategoryId();
        SubjectMapping subjectMapping = new SubjectMapping();
        subjectMapping.setCategoryId(categoryId);
        subjectMapping.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        List<SubjectMapping> subjectMappingList = subjectMappingService.queryDistinctLabelId(subjectMapping);
        log.info("subjectMappingList:{}", JSON.toJSONString(subjectMappingList));
        if (CollectionUtils.isEmpty(subjectMappingList)) {
            return Collections.emptyList();
        }
        List<Long> labelIds = subjectMappingList.stream()
                .map(SubjectMapping::getLabelId)
                .collect(Collectors.toList());
        List<SubjectLabel> labelList = subjectLabelService.batchQueryByIds(labelIds);
        List<SubjectLabelBO> boList = SubjectLabelConverter.INSTANCE.convertLabelListToBoList(labelList);
        boList.forEach(bo -> bo.setCategoryId(categoryId));
        return boList;
    }
}
