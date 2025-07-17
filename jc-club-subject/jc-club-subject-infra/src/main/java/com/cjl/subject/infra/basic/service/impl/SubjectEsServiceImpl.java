package com.cjl.subject.infra.basic.service.impl;

import com.cjl.subject.common.entity.PageResult;
import com.cjl.subject.common.enums.SubjectTypeEnum;
import com.cjl.subject.infra.basic.entity.EsSubjectFields;
import com.cjl.subject.infra.basic.entity.SubjectInfoEs;
import com.cjl.subject.infra.basic.es.EsIndexInfo;
import com.cjl.subject.infra.basic.es.EsRestClient;
import com.cjl.subject.infra.basic.es.EsSearchRequest;
import com.cjl.subject.infra.basic.es.EsSourceData;
import com.cjl.subject.infra.basic.service.SubjectEsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-07-16-17:05
 * @Description
 */
@Service
@Slf4j
public class SubjectEsServiceImpl implements SubjectEsService {
    public static final String CLUSTER_NAME = "PDPbuXN6SDmok8lUnmCbtA";
    public static final String INDEX_NAME = "subject_index";

    @Override
    public Boolean insert(SubjectInfoEs subjectInfoEs) {
        Map<String, Object> data = convertEsEntity2SourceData(subjectInfoEs);
        EsSourceData esSourceData = new EsSourceData();
        esSourceData.setDocId(subjectInfoEs.getDocId().toString());
        esSourceData.setData(data);
        return EsRestClient.insertDoc(getEsIndexInfo(), esSourceData);
    }

    /**
     * 在es中查询文档信息
     */
    @Override
    public PageResult<SubjectInfoEs> querySubjectList(SubjectInfoEs req) {
        PageResult<SubjectInfoEs> pageResult = new PageResult<>();
        EsSearchRequest esSearchRequest = createSearchListQuery(req);
        SearchResponse searchResponse = EsRestClient.searchWithTermQuery(getEsIndexInfo(), esSearchRequest);
        List<SubjectInfoEs> subjectInfoEsList = new ArrayList<>();
        if (searchResponse == null || searchResponse.getHits().getHits().length == 0) {
            pageResult.setPageNo(req.getPageNo());
            pageResult.setPageSize(req.getPageSize());
            pageResult.setTotal(0);
            pageResult.setResult(subjectInfoEsList);
            return pageResult;
        }
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            SubjectInfoEs subjectInfoEs = convertSourceData2EsEntity(hit.getSourceAsMap());
            if (Objects.nonNull(subjectInfoEs)) {
                subjectInfoEs.setScore(BigDecimal.valueOf(hit.getScore())
                        .multiply(BigDecimal.valueOf(100))
                        .setScale(2, RoundingMode.HALF_UP)
                );
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();

                //处理题目高亮
                HighlightField subjectNameField = highlightFields.get(EsSubjectFields.SUBJECT_NAME);
                if (Objects.nonNull(subjectNameField)) {
                    StringBuilder highlightSubjectName = new StringBuilder();
                    for (Text text : subjectNameField.getFragments()) {
                        highlightSubjectName.append(text);
                    }
                    subjectInfoEs.setSubjectName(highlightSubjectName.toString());
                }

                //处理答案高亮
                HighlightField subjectAnswerField = highlightFields.get(EsSubjectFields.SUBJECT_ANSWER);
                if (Objects.nonNull(subjectAnswerField)) {
                    StringBuilder highlightSubjectAnswer = new StringBuilder();
                    for (Text text : subjectAnswerField.getFragments()) {
                        highlightSubjectAnswer.append(text);
                    }
                    subjectInfoEs.setSubjectAnswer(highlightSubjectAnswer.toString());
                }
                subjectInfoEsList.add(subjectInfoEs);
            }
        }
        pageResult.setPageNo(req.getPageNo());
        pageResult.setPageSize(req.getPageSize());
        pageResult.setTotal(subjectInfoEsList.size());
        pageResult.setRecords(subjectInfoEsList);
        return pageResult;
    }

    private Map<String, Object> convertEsEntity2SourceData(SubjectInfoEs subjectInfoEs) {
        Map<String, Object> data = new HashMap<>();
        data.put(EsSubjectFields.DOC_ID, subjectInfoEs.getDocId());
        data.put(EsSubjectFields.SUBJECT_ID, subjectInfoEs.getSubjectId());
        data.put(EsSubjectFields.SUBJECT_NAME, subjectInfoEs.getSubjectName());
        data.put(EsSubjectFields.SUBJECT_ANSWER, subjectInfoEs.getSubjectAnswer());
        data.put(EsSubjectFields.SUBJECT_TYPE, subjectInfoEs.getSubjectType());
        data.put(EsSubjectFields.CREATE_TIME, subjectInfoEs.getCreateTime());
        data.put(EsSubjectFields.CREATE_USER, subjectInfoEs.getCreateUser());
        return data;
    }

    private SubjectInfoEs convertSourceData2EsEntity(Map<String, Object> sourceAsMap) {
        if (ObjectUtils.isEmpty(sourceAsMap)) {
            return null;
        }
        SubjectInfoEs result = new SubjectInfoEs();
        result.setDocId(MapUtils.getLong(sourceAsMap, EsSubjectFields.DOC_ID));
        result.setSubjectId(MapUtils.getLong(sourceAsMap, EsSubjectFields.SUBJECT_ID));
        result.setSubjectName(MapUtils.getString(sourceAsMap, EsSubjectFields.SUBJECT_NAME));
        result.setSubjectAnswer(MapUtils.getString(sourceAsMap, EsSubjectFields.SUBJECT_ANSWER));
        result.setSubjectType(MapUtils.getInteger(sourceAsMap, EsSubjectFields.SUBJECT_TYPE));
        result.setCreateTime(MapUtils.getLong(sourceAsMap, EsSubjectFields.CREATE_TIME));
        result.setCreateUser(MapUtils.getString(sourceAsMap, EsSubjectFields.CREATE_USER));
        return result;
    }


    private EsSearchRequest createSearchListQuery(SubjectInfoEs req) {
        BoolQueryBuilder bq = new BoolQueryBuilder();
        MatchQueryBuilder subjectNameQuery = QueryBuilders.matchQuery(
                EsSubjectFields.SUBJECT_NAME,
                req.getKeyWord());
        subjectNameQuery.boost(2);
        bq.should(subjectNameQuery);

        MatchQueryBuilder subjectAnswerQuery = QueryBuilders.matchQuery(
                EsSubjectFields.SUBJECT_ANSWER,
                req.getKeyWord());
        bq.should(subjectAnswerQuery);

        if(Objects.isNull(req.getSubjectType())) {
            req.setSubjectType(SubjectTypeEnum.BRIEF.getCode());
        }
        MatchQueryBuilder subjectTypeQuery = QueryBuilders.matchQuery(
                EsSubjectFields.SUBJECT_TYPE,
                req.getSubjectType());
        bq.must(subjectTypeQuery);
        bq.minimumShouldMatch(1);

        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .field("*")
                .requireFieldMatch(false)
                .preTags("<span style=\"color:red\">")
                .postTags("</span>");

        return EsSearchRequest.builder()
                .bq(bq)
                .highlightBuilder(highlightBuilder)
                .fields(EsSubjectFields.FIELDS_QUERY)
                .from((req.getPageNo()-1) * req.getPageSize())
                .size(req.getPageSize())
                .needScroll(false)
                .build();
    }

    private EsIndexInfo getEsIndexInfo() {
        EsIndexInfo esIndexInfo = new EsIndexInfo();
        esIndexInfo.setClusterName(CLUSTER_NAME);
        esIndexInfo.setIndexName(INDEX_NAME);
        return esIndexInfo;
    }
}
