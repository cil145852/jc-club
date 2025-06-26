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

    /**
     * 修改题目分类
     * @param subjectCategoryBO
     * @return
     */
    Boolean update(SubjectCategoryBO subjectCategoryBO);

    /**
     * 删除题目分类
     * @param subjectCategoryBO
     * @return
     */
    Boolean delete(SubjectCategoryBO subjectCategoryBO);

    /**
     * 查询大分类下的分类和标签
     */
    List<SubjectCategoryBO> queryCategoryAndLabel(SubjectCategoryBO subjectCategoryBO);
}
