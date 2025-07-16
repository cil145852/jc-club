package com.cjl.subject.infra.basic.service.impl;

import com.cjl.subject.common.entity.PageResult;
import com.cjl.subject.infra.basic.entity.EsSubjectFields;
import com.cjl.subject.infra.basic.entity.SubjectInfoEs;
import com.cjl.subject.infra.basic.es.EsIndexInfo;
import com.cjl.subject.infra.basic.es.EsRestClient;
import com.cjl.subject.infra.basic.es.EsSourceData;
import com.cjl.subject.infra.basic.service.SubjectEsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
        Map<String, Object> data = convert2EsSourceData(subjectInfoEs);
        EsSourceData esSourceData = new EsSourceData();
        esSourceData.setDocId(subjectInfoEs.getDocId().toString());
        esSourceData.setData(data);
        return EsRestClient.insertDoc(getEsIndexInfo(), esSourceData);
    }

    private Map<String, Object> convert2EsSourceData(SubjectInfoEs subjectInfoEs) {
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

    @Override
    public PageResult<SubjectInfoEs> querySubjectList(SubjectInfoEs subjectInfoEs) {
        return null;
    }

    private EsIndexInfo getEsIndexInfo() {
        EsIndexInfo esIndexInfo = new EsIndexInfo();
        esIndexInfo.setClusterName(CLUSTER_NAME);
        esIndexInfo.setIndexName(INDEX_NAME);
        return esIndexInfo;
    }
}
