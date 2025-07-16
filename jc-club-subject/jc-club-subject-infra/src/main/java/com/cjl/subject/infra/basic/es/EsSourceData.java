package com.cjl.subject.infra.basic.es;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-07-16-9:54
 * @Description
 */
@Data
public class EsSourceData implements Serializable {
    /**
     * 文档id
     */
    private String docId;

    /**
     * 文档数据
     */
    private Map<String, Object> data;
}

