package com.cjl.subject.domain.handler.subject;

import com.cjl.subject.common.enums.IsDeletedFlagEnum;
import com.cjl.subject.common.enums.SubjectTypeEnum;
import com.cjl.subject.domain.convert.SubjectMultipleConverter;
import com.cjl.subject.domain.entity.SubjectInfoBO;
import com.cjl.subject.infra.basic.entity.SubjectMultiple;
import com.cjl.subject.infra.basic.service.SubjectMultipleService;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-04-9:26
 * @Description 多选题策略
 */
@Component
public class MultipleHandler implements SubjectTypeHandler{
    @Resource
    private SubjectMultipleService subjectMultipleService;

    @Override
    public SubjectTypeEnum getSubjectType() {
        return SubjectTypeEnum.MULTIPLE;
    }

    @Override
    public void add(SubjectInfoBO subjectInfoBO) {
        if (CollectionUtils.isEmpty(subjectInfoBO.getOptionList())) {
            throw new RuntimeException("多选题选项不能为空");
        }
        Long subjectId = subjectInfoBO.getId();
        List<SubjectMultiple> subjectMultipleList = subjectInfoBO.getOptionList()
                .stream()
                .map(option -> {
                    SubjectMultiple multiple = SubjectMultipleConverter.INSTANCE.convertBoToEntity(option);
                    multiple.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
                    multiple.setSubjectId(subjectId);
                    return multiple;
                })
                .collect(Collectors.toList());
        subjectMultipleService.batchInsert(subjectMultipleList);
    }
}
