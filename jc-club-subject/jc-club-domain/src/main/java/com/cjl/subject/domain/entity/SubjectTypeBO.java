package com.cjl.subject.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-06-20:07
 * @Description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectTypeBO {
    /**
     * 题目答案
     */
    private String subjectAnswer;
    /**
     * 选项列表
     */
    private List<SubjectOptionBO> optionList;
}
