package com.cjl.subject.infra.basic.es;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-07-16-9:04
 * @Description
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EsSearchRequest {
    /**
     * 查询条件
     */
    private BoolQueryBuilder bq;

    /**
     * 查询字段
     */
    private String[] fields;

    /**
     * 分页查询开始位置
     */
    private Integer from;

    /**
     * 分页查询数量
     */
    private Integer size;

    /**
     * 是否创建快照
     */
    private Boolean needScroll;

    /**
     * 快照缓存时间,单位: 分钟
     */
    private Long minutes;

    /**
     * 排序字段
     */
    private String sortName;

    /**
     * 排序方式
     */
    private SortOrder sortOrder;

    /**
     * 高亮规则builder
     */
    private HighlightBuilder highlightBuilder;

    public Boolean isNeedScroll() {
        return this.needScroll;
    }
}
