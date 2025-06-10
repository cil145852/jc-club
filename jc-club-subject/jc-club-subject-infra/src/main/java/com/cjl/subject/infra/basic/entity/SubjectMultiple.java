package com.cjl.subject.infra.basic.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 多选题信息表(SubjectMultiple)实体类
 *
 * @author makejava
 * @since 2025-06-04 08:16:40
 */
@Data
public class SubjectMultiple implements Serializable {
    private static final long serialVersionUID = -24436589949334349L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 题目id
     */
    private Long subjectId;
    /**
     * 选项类型 1A 2B 3C 4D
     */
    private Integer optionType;
    /**
     * 选项内容
     */
    private String optionContent;
    /**
     * 是否正确 0否 1是
     */
    private Integer isCorrect;
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

