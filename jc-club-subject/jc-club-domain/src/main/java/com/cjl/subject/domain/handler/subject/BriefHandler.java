package com.cjl.subject.domain.handler.subject;

import com.cjl.subject.common.enums.IsDeletedFlagEnum;
import com.cjl.subject.common.enums.SubjectTypeEnum;
import com.cjl.subject.domain.entity.SubjectInfoBO;
import com.cjl.subject.infra.basic.entity.SubjectBrief;
import com.cjl.subject.infra.basic.service.SubjectBriefService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-04-9:26
 * @Description 简答题策略
 */
@Component
public class BriefHandler implements SubjectTypeHandler {
    @Resource
    private SubjectBriefService subjectBriefService;
    @Override
    public SubjectTypeEnum getSubjectType() {
        return SubjectTypeEnum.BRIEF;
    }

    @Override
    public void add(SubjectInfoBO subjectInfoBO) {
        SubjectBrief subjectBrief = SubjectBrief.builder()
                .subjectId(subjectInfoBO.getId())
                .subjectAnswer(subjectInfoBO.getSubjectAnswer())
                .isDeleted(IsDeletedFlagEnum.UN_DELETED.getCode())
                .build();
        subjectBriefService.insert(subjectBrief);
    }
}
