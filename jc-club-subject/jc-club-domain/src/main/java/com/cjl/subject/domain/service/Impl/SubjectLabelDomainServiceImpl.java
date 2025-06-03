package com.cjl.subject.domain.service.Impl;

import com.cjl.subject.common.enums.IsDeletedFlagEnum;
import com.cjl.subject.domain.convert.SubjectLabelConverter;
import com.cjl.subject.domain.entity.SubjectLabelBO;
import com.cjl.subject.domain.service.SubjectLabelDomainService;
import com.cjl.subject.infra.basic.entity.SubjectLabel;
import com.cjl.subject.infra.basic.service.SubjectLabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    /**
     * 添加标签
     *
     * @param subjectLabelBO
     * @return
     */
    @Override
    public Boolean add(SubjectLabelBO subjectLabelBO) {
        SubjectLabel subjectLabel = SubjectLabelConverter.INSTANCE.convertBoToLabel(subjectLabelBO);
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

    @Override
    public Boolean delete(SubjectLabelBO subjectLabelBO) {
        SubjectLabel subjectLabel = SubjectLabelConverter.INSTANCE.convertBoToLabel(subjectLabelBO);
        subjectLabel.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        Integer count = subjectLabelService.update(subjectLabel);
        return count > 0;
    }
}
