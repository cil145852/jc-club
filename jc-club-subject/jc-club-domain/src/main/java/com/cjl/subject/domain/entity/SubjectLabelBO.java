package com.cjl.subject.domain.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 题目标签bo
 */
@Data
public class SubjectLabelBO implements Serializable {
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
}

