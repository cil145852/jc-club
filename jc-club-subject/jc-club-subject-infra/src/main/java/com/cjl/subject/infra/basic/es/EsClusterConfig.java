package com.cjl.subject.infra.basic.es;

import lombok.Data;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-07-16-8:54
 * @Description es集群类
 */
@Data
public class EsClusterConfig {
    /**
     * 集群名称
     */
    private String name;

    /**
     * 集群节点(使用,分隔)
     */
    private String nodes;
}
