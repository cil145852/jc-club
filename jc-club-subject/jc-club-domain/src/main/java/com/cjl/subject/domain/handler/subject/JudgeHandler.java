package com.cjl.subject.domain.handler.subject;

import com.cjl.subject.common.enums.IsDeletedFlagEnum;
import com.cjl.subject.common.enums.SubjectTypeEnum;
import com.cjl.subject.domain.entity.SubjectInfoBO;
import com.cjl.subject.infra.basic.entity.SubjectJudge;
import com.cjl.subject.infra.basic.service.SubjectJudgeService;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-04-9:26
 * @Description 判断题策略
 */
@Component
public class JudgeHandler implements SubjectTypeHandler {
    @Resource
    private SubjectJudgeService subjectJudgeService;

    @Override
    public SubjectTypeEnum getSubjectType() {
        return SubjectTypeEnum.JUDGE;
    }

    @Override
    public void add(SubjectInfoBO subjectInfoBO) {
        if (CollectionUtils.isEmpty(subjectInfoBO.getOptionList())) {
            throw new RuntimeException("判断题选项不能为空");
        }
        Long subjectId = subjectInfoBO.getId();
        List<SubjectJudge> subjectJudgeList = subjectInfoBO.getOptionList()
                .stream()
                .map(option -> SubjectJudge.builder()
                        .subjectId(subjectId)
                        .isCorrect(option.getIsCorrect())
                        .isDeleted(IsDeletedFlagEnum.UN_DELETED.getCode())
                        .build())
                .collect(Collectors.toList());
        subjectJudgeService.batchInsert(subjectJudgeList);
    }
}
