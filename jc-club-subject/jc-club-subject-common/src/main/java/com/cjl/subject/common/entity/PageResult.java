package com.cjl.subject.common.entity;

import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-06-8:12
 * @Description 分页返回实体类
 */
@Data
public class PageResult<T> implements Serializable {
    private Integer pageNo = 1;

    private Integer pageSize = 20;

    private Integer total = 0;

    private Integer totalPages = 0;

    private List<T> result = Collections.emptyList();

    private Integer start = 0;

    private Integer end = 0;

    public void setRecords(List<T> records) {
        this.result = records;
        if (ObjectUtils.isNotEmpty(records)) {
            setTotal(records.size());
        }
    }

    public void setTotal(Integer total) {
        this.total = total;
        if (pageSize > 0) {
            this.totalPages = (total + pageSize - 1) / pageSize;
        } else {
            this.totalPages = 0;
        }
        if (pageNo > 0 && pageSize > 0) {
            this.start = (pageNo - 1) * pageSize + 1;
            this.end = Math.min(this.start + pageSize - 1, total);
        } else {
            this.start = 1;
            this.end = total;
        }
    }
}
