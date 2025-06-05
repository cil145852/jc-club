package com.cjl.subject.domain.entity;

import lombok.Data;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-04-8:48
 * 题目选项bo
 */
@Data
public class SubjectOptionBO {
    /**
     * 选项类型
     */
    private Integer optionType;
    /**
     * 选项内容
     */
    private String optionContent;
    /**
     * 是否正确
     */
    private Integer isCorrect;
}
