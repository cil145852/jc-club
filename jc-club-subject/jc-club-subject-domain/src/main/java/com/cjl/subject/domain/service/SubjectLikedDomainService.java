package com.cjl.subject.domain.service;

import com.cjl.subject.common.entity.PageResult;
import com.cjl.subject.domain.entity.SubjectLikedBO;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-07-17-21:27
 * @Description
 */

public interface SubjectLikedDomainService {

    /**
     * 添加 题目点赞表 信息
     */
    void add(SubjectLikedBO subjectLikedBO);

    /**
     * 获取当前是否被点赞过
     */
    Boolean isLiked(Long subjectId, String userId);

    /**
     * 获取点赞数量
     */
    Integer getLikedCount(Long subjectId);

    /**
     * 更新 题目点赞表 信息
     */
    Boolean update(SubjectLikedBO subjectLikedBO);

    /**
     * 删除 题目点赞表 信息
     */
    Boolean delete(SubjectLikedBO subjectLikedBO);

    /**
     * 同步点赞数据
     */
    void syncLiked();

    PageResult<SubjectLikedBO> getSubjectLikedPage(SubjectLikedBO subjectLikedBO);

}