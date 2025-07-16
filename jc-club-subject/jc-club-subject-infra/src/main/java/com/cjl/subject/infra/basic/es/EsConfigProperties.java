package com.cjl.subject.infra.basic.es;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-07-16-8:55
 * @Description
 */
@Component
@ConfigurationProperties(prefix = "es.cluster")
@Data
public class EsConfigProperties {
    private List<EsClusterConfig> esConfigs = new ArrayList<>();

}
