package com.cjl.subject.infra.basic.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 单选题信息表(SubjectRadio)实体类
 *
 * @author makejava
 * @since 2025-06-04 08:16:56
 */
@Data
public class SubjectRadio implements Serializable {
    private static final long serialVersionUID = -25907128235836277L;
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

