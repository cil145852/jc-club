package com.cjl.subject.domain.entity;

import lombok.Data;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-01-21:06
 * @Description
 */
@Data
public class SubjectCategoryBO {
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
}
