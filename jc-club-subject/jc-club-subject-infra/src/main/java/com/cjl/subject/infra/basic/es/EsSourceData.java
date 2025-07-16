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
    private String docId;

    private Map<String, Object> data;
}

