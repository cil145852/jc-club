package com.cjl.subject.domain.service;

import com.cjl.subject.domain.entity.SubjectLabelBO;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-03-17:06
 * @Description
 */

public interface SubjectLabelDomainService {
    /**
     * 添加标签
     *
     * @param subjectLabelBO
     */
    Boolean add(SubjectLabelBO subjectLabelBO);
}
