package com.cjl.subject.infra.basic.es;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    public static final RequestOptions COMMON_OPTIONS = RequestOptions.DEFAULT;

    @PostConstruct
    public void initialize() {
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

    private static RestHighLevelClient getClient(String clusterName) {
        return clientMap.get(clusterName);
    }

    /**
     * 插入文档信息
     */
    public static Boolean insertDoc(EsIndexInfo esIndexInfo, EsSourceData esSourceData) {
        try {
            IndexRequest indexRequest = new IndexRequest(esIndexInfo.getIndexName());
            indexRequest.source(esSourceData.getData());
            indexRequest.id(esSourceData.getDocId());
            getClient(esIndexInfo.getClusterName()).index(indexRequest, COMMON_OPTIONS);
            return true;
        } catch (IOException e) {
            log.error("insertDoc.exception:{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 删除索引库中所有文档信息
     */
    public static Boolean delete(EsIndexInfo esIndexInfo) {
        try {
            DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest(esIndexInfo.getIndexName());
            deleteByQueryRequest.setQuery(QueryBuilders.matchAllQuery());
            BulkByScrollResponse response = getClient(esIndexInfo.getClusterName())
                    .deleteByQuery(deleteByQueryRequest, COMMON_OPTIONS);
            long deleted = response.getDeleted();
            log.info("deleted.size:{}", deleted);
            return true;
        } catch (Exception e) {
            log.error("delete.exception:{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 根据文档id删除文档信息
     */
    public static Boolean deleteByDocId(EsIndexInfo esIndexInfo, String docId) {
        try {
            DeleteRequest deleteRequest = new DeleteRequest(esIndexInfo.getIndexName());
            deleteRequest.id(docId);
            DeleteResponse response = getClient(esIndexInfo.getClusterName()).delete(deleteRequest, COMMON_OPTIONS);
            log.info("deleteByDocId.response:{}", JSON.toJSONString(response));
            return true;
        } catch (Exception e) {
            log.error("deleteByDocId.exception:{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 查看文档是否存在
     */
    public static Boolean isExistDocById(EsIndexInfo esIndexInfo, String docId) {
        try {
            GetRequest getRequest = new GetRequest(esIndexInfo.getIndexName());
            getRequest.id(docId);
            return getClient(esIndexInfo.getClusterName()).exists(getRequest, COMMON_OPTIONS);
        } catch (Exception e) {
            log.error("isExistDocById.exception:{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 根据文档id获取文档所有信息
     */
    public static Map<String, Object> getDocById(EsIndexInfo esIndexInfo, String docId) {
        try {
            GetRequest getRequest = new GetRequest(esIndexInfo.getIndexName());
            getRequest.id(docId);
            GetResponse response = getClient(esIndexInfo.getClusterName()).get(getRequest, COMMON_OPTIONS);
            return response.getSource();
        } catch (Exception e) {
            log.error("isExistDocById.exception:{}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 根据文档id获取文档指定字段信息
     */
    public static Map<String, Object> getDocById(EsIndexInfo esIndexInfo, String docId, String[] fields) {
        try {
            GetRequest getRequest = new GetRequest(esIndexInfo.getIndexName());
            getRequest.id(docId);
            FetchSourceContext fetchSourceContext = new FetchSourceContext(true, fields, null);
            getRequest.fetchSourceContext(fetchSourceContext);
            GetResponse response = getClient(esIndexInfo.getClusterName()).get(getRequest, COMMON_OPTIONS);
            return response.getSource();
        } catch (Exception e) {
            log.error("isExistDocById.exception:{}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 搜索文档信息
     */
    public static SearchResponse searchWithTermQuery(EsIndexInfo esIndexInfo,
                                                     EsSearchRequest esSearchRequest) {
        try {
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices(esIndexInfo.getIndexName())
                    .source(getSearchSourceBuilder(esSearchRequest))
                    .searchType(SearchType.DEFAULT);
            if (esSearchRequest.isNeedScroll()) {
                Scroll scroll = new Scroll(TimeValue.timeValueMinutes(esSearchRequest.getMinutes()));
                searchRequest.scroll(scroll);
            }
            return getClient(esIndexInfo.getClusterName()).search(searchRequest, COMMON_OPTIONS);
        } catch (Exception e) {
            log.error("searchWithTermQuery.exception:{}", e.getMessage(), e);
            return null;
        }
    }

    private static SearchSourceBuilder getSearchSourceBuilder(EsSearchRequest esSearchRequest) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        HighlightBuilder highlightBuilder = esSearchRequest.getHighlightBuilder();
        String sortName = esSearchRequest.getSortName();
        SortOrder sortOrder = esSearchRequest.getSortOrder();
        searchSourceBuilder
                .fetchSource(esSearchRequest.getFields(), null)
                .from(esSearchRequest.getFrom())
                .size(esSearchRequest.getSize())
                .query(esSearchRequest.getBq());
        if (Objects.nonNull(highlightBuilder)) {
            searchSourceBuilder.highlighter(highlightBuilder);
        }
        if (StringUtils.isNotBlank(sortName)) {
            searchSourceBuilder.sort(sortName, sortOrder != null ? sortOrder : SortOrder.DESC);
        } else {
            searchSourceBuilder.sort(SortBuilders.scoreSort());
        }
        return searchSourceBuilder;
    }

    /**
     * 更新文档信息
     */
    public static Boolean updateDoc(EsIndexInfo esIndexInfo,
                                    EsSourceData esSourceData) {
        try {
            UpdateRequest updateRequest = new UpdateRequest(esIndexInfo.getIndexName(), esSourceData.getDocId());
            updateRequest.doc(esSourceData.getData());
            getClient(esIndexInfo.getClusterName()).update(updateRequest, COMMON_OPTIONS);
            return true;
        } catch (Exception e) {
            log.error("updateDoc.exception:{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 批量更新文档信息
     */
    public static Boolean batchUpdateDoc(EsIndexInfo esIndexInfo,
                                    List<EsSourceData> esSourceDataList) {
        try {
            boolean needUpdate = false;
            BulkRequest bulkRequest = new BulkRequest();
            for (EsSourceData esSourceData : esSourceDataList) {
                String docId = esSourceData.getDocId();
                if (StringUtils.isNotBlank(docId)) {
                    UpdateRequest updateRequest = new UpdateRequest(esIndexInfo.getIndexName(), docId);
                    updateRequest.doc(esSourceData.getData());
                    bulkRequest.add(updateRequest);
                    needUpdate = true;
                }
            }
            if (needUpdate) {
                BulkResponse bulkResponse = getClient(esIndexInfo.getClusterName()).bulk(bulkRequest, COMMON_OPTIONS);
                return !bulkResponse.hasFailures();
            }
            return true;
        } catch (Exception e) {
            log.error("batchUpdateDoc.exception:{}", e.getMessage(), e);
            return false;
        }
    }

}
