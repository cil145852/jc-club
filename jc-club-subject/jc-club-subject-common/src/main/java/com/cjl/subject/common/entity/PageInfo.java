package com.cjl.subject.common.entity;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-06-8:07
 * @Description 分页请求实体类
 */

public class PageInfo {
    private Integer pageNo = 1;
    private Integer pageSize = 20;

    public Integer getPageNo() {
        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
        }
        return pageNo;
    }

    public Integer getPageSize() {
        if (pageSize == null || pageSize < 1 || pageSize > 200) {
            pageSize = 20;
        }
        return pageSize;
    }
}
