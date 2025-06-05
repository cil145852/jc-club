package com.cjl.subject.infra.basic.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 简答题信息表(SubjectBrief)实体类
 *
 * @author makejava
 * @since 2025-06-04 08:15:47
 */
@Data
@Builder
public class SubjectBrief implements Serializable {
    private static final long serialVersionUID = 869262839792328409L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 题目答案
     */
    private String subjectAnswer;
    /**
     * 题目id
     */
    private Long subjectId;
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

