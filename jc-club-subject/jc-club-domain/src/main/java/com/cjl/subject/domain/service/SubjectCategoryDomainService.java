package com.cjl.subject.domain.service;

import com.cjl.subject.domain.entity.SubjectCategoryBO;

import java.util.List;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-01-20:54
 * @Description
 */

public interface SubjectCategoryDomainService {
    /**
     * 添加题目分类
     * @param subjectCategoryBO
     */
    void add(SubjectCategoryBO subjectCategoryBO);

    /**
     * 查询一级分类
     * @return
     */
    List<SubjectCategoryBO> queryCategory(SubjectCategoryBO subjectCategoryBO);
}
