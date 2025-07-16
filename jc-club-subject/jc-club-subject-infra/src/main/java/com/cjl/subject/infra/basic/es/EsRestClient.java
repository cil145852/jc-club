package com.cjl.subject.infra.basic.es;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-07-16-9:56
 * @Description es客户端, 是对RestHighLevelClient的封装
 */
@Component
@Slf4j
@Data
public class EsRestClient {
   /**
    * 给每个集群分配一个客户端
    */
   private static Map<String, RestHighLevelClient> clientMap = new HashMap<>();

   @Resource
   private EsConfigProperties esConfigProperties;

   @PostConstruct
   public void initialize(){
      List<EsClusterConfig> esConfigs = esConfigProperties.getEsConfigs();
      for (EsClusterConfig esConfig : esConfigs) {
         log.info("initialize.config.name:{},node:{}", esConfig.getName(), esConfig.getNodes());
         RestHighLevelClient restHighLevelClient = initRestClient(esConfig);
          clientMap.put(esConfig.getName(), restHighLevelClient);
      }
   }

   private RestHighLevelClient initRestClient(EsClusterConfig esConfig) {
      String[] hosts = esConfig.getNodes().split(",");
      HttpHost[] httpHosts = Stream.of(hosts)
              .map(host -> {
                 String[] hostAndPort = host.split(":");
                 if (hostAndPort.length != 2) {
                   throw new IllegalArgumentException("Invalid host: " + host);
                 }
                 return new HttpHost(hostAndPort[0], Integer.parseInt(hostAndPort[1]));
              })
              .toArray(HttpHost[]::new);
      return new RestHighLevelClient(RestClient.builder(httpHosts));
   }
}
