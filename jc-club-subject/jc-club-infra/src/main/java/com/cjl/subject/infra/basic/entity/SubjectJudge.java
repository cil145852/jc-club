package com.cjl.subject.infra.basic.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 判断题信息表(SubjectJudge)实体类
 *
 * @author makejava
 * @since 2025-06-04 08:16:16
 */
@Data
@Builder
public class SubjectJudge implements Serializable {
    private static final long serialVersionUID = 863621855800973029L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 题目id
     */
    private Long subjectId;
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

