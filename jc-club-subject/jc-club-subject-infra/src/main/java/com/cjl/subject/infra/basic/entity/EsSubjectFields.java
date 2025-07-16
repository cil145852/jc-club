package com.cjl.subject.infra.basic.entity;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-07-16-16:57
 * @Description
 */

public class EsSubjectFields {
    public static final String DOC_ID = "doc_id";

    public static final String SUBJECT_ID = "subject_id";

    public static final String SUBJECT_NAME = "subject_name";

    public static final String SUBJECT_ANSWER = "subject_answer";

    public static final String SUBJECT_TYPE = "subject_type";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_USER = "create_user";

    public static final String[] FIELDS_QUERY = {
            DOC_ID,
            SUBJECT_ID,
            SUBJECT_NAME,
            SUBJECT_ANSWER,
            SUBJECT_TYPE,
            CREATE_TIME,
            CREATE_USER
    };
}
