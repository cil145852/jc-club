package com.cjl.subject.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 题目标签dto
 */
@Data
public class SubjectLabelDTO implements Serializable {
    private static final long serialVersionUID = -31021113497242037L;
/**
     * 主键
     */
    private Long id;
/**
     * 标签名称
     */
    private String labelName;
/**
     * 排序
     */
    private Integer sortNum;

    /**
     * 题目分类id
     */
    private Long categoryId;
}

