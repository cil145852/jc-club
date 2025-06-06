package com.cjl.subject.domain.handler.subject;

import com.cjl.subject.common.enums.SubjectTypeEnum;
import com.cjl.subject.domain.entity.SubjectInfoBO;
import com.cjl.subject.domain.entity.SubjectTypeBO;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-04-9:26
 * @Description 题目类型的策略接口
 */

public interface SubjectTypeHandler {
    /**
     * 标识策略的类型
     *
     * @return
     */
    SubjectTypeEnum getSubjectType();

    /**
     * 添加试题
     *
     * @param subjectInfoBO
     */
    void add(SubjectInfoBO subjectInfoBO);

    /**
     * 查询试题信息
     *
     * @param id
     * @return
     */
    SubjectTypeBO query(Long subjectId);
}
