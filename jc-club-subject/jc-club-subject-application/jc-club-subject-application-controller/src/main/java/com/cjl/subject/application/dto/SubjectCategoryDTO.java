package com.cjl.subject.application.dto;

import lombok.Data;

import java.util.List;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-02-20:45
 * @Description
 */

@Data
public class SubjectCategoryDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类类型
     */
    private Integer categoryType;

    /**
     * 图片链接
     */
    private String imageUrl;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 分类下的题目数量
     */
    private Integer count;

    /**
     * 分类相关的标签
     */
    private List<SubjectLabelDTO> subjectLabelDTOList;
}
