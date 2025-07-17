package com.cjl.subject.domain.service;

import com.cjl.subject.common.entity.PageResult;
import com.cjl.subject.domain.entity.SubjectInfoBO;

import java.util.List;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-04-9:04
 * @Description
 */

public interface SubjectInfoDomainService {

   void add(SubjectInfoBO subjectInfoBO);

    PageResult<SubjectInfoBO> getSubjectPage(SubjectInfoBO subjectInfoBO);

    SubjectInfoBO querySubjectInfo(SubjectInfoBO subjectInfoBO);

    PageResult<SubjectInfoBO> getSubjectPageBySearch(SubjectInfoBO subjectInfoBO);

    List<SubjectInfoBO> getContributeList();
}
