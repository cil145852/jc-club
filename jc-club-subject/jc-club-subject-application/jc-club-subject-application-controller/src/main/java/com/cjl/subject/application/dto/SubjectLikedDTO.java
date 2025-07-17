package com.cjl.subject.application.dto;

import com.cjl.subject.common.entity.PageInfo;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-07-17-21:34
 * @Description
 */

@Data
public class SubjectLikedDTO extends PageInfo implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 题目id
     */
    private Long subjectId;

    /**
     * 题目名称
     */
    private String subjectName;

    /**
     * 点赞人id
     */
    private String likeUserId;

    /**
     * 点赞状态 1点赞 0不点赞
     */
    private Integer status;
}