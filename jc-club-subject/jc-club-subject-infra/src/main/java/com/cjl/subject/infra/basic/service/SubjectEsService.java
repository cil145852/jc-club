package com.cjl.subject.infra.basic.service;

import com.cjl.subject.common.entity.PageResult;
import com.cjl.subject.infra.basic.entity.SubjectInfoEs;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-07-16-16:52
 * @Description
 */

public interface SubjectEsService {

    Boolean insert(SubjectInfoEs subjectInfoEs);

    PageResult<SubjectInfoEs> querySubjectList(SubjectInfoEs subjectInfoEs);
}
