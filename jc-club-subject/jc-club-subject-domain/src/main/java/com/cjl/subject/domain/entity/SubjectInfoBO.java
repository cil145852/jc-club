package com.cjl.subject.domain.entity;

import com.cjl.subject.common.entity.PageInfo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-04-8:48
 * 题目bo
 */
@Data
public class SubjectInfoBO extends PageInfo implements Serializable {
    private static final long serialVersionUID = -68638031646127733L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 题目名称
     */
    private String subjectName;
    /**
     * 题目难度
     */
    private Integer subjectDifficult;
    /**
     * 出题人名
     */
    private String settleName;
    /**
     * 题目类型 1单选 2多选 3判断 4简答
     */
    private Integer subjectType;
    /**
     * 题目分数
     */
    private Integer subjectScore;
    /**
     * 题目解析
     */
    private String subjectParse;
    /**
     * 题目答案
     */
    private String subjectAnswer;
    /**
     * 分类id
     */
    private List<Long> categoryIds;
    /**
     * 标签id
     */
    private List<Long> labelIds;
    /**
     * 标签name
     */
    private List<String> labelName;
    /**
     * 选项列表
     */
    private List<SubjectOptionBO> optionList;
    /**
     * 分类id
     */
    private Long categoryId;
    /**
     * 标签id
     */
    private Long labelId;
    /**
     * es搜索关键字
     */
    private String keyWord;

    /**
     * es搜索分数
     */
    private BigDecimal score;

    /**
     * es文档id
     */
    private Long docId;
}
