package com.cjl.subject.infra.basic.entity;

import com.cjl.subject.common.entity.PageInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-07-16-16:51
 * @Description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SubjectInfoEs extends PageInfo implements Serializable {
    private Long subjectId;

    private Long docId;

    private String subjectName;

    private String subjectAnswer;

    private String createUser;

    private Long createTime;

    private Integer subjectType;

    private String keyWord;

    private BigDecimal score;

}
