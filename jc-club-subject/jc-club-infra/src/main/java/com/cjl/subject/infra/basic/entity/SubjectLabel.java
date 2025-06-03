package com.cjl.subject.infra.basic.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 题目标签表(SubjectLabel)实体类
 *
 * @author makejava
 * @since 2025-06-03 16:48:07
 */
@Data
public class SubjectLabel implements Serializable {
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
     * 创建人
     */
    private String createdBy;
/**
     * 创建时间
     */
    private Date createdTime;
/**
     * 更新人
     */
    private String updateBy;
/**
     * 更新时间
     */
    private Date updateTime;
/**
     * 是否已删除 0否 1是
     */
    private Integer isDeleted;
}

