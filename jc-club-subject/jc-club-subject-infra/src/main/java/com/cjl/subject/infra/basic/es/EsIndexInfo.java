package com.cjl.subject.infra.basic.es;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-07-16-9:02
 * @Description es索引类
 */
@Data
public class EsIndexInfo implements Serializable {
    /**
     * 集群名称
     */
    private String clusterName;

    /**
     * 索引名称
     */
    private String indexName;
}
