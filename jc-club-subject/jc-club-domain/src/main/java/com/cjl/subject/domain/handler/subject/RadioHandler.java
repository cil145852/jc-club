package com.cjl.subject.domain.handler.subject;

import com.cjl.subject.common.enums.IsDeletedFlagEnum;
import com.cjl.subject.common.enums.SubjectTypeEnum;
import com.cjl.subject.domain.convert.SubjectRadioConverter;
import com.cjl.subject.domain.entity.SubjectInfoBO;
import com.cjl.subject.infra.basic.entity.SubjectRadio;
import com.cjl.subject.infra.basic.service.SubjectRadioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-04-9:26
 * @Description 单选题策略
 */
@Slf4j
@Component
public class RadioHandler implements SubjectTypeHandler {
    @Resource
    private SubjectRadioService subjectRadioService;

    @Override
    public SubjectTypeEnum getSubjectType() {
        return SubjectTypeEnum.RADIO;
    }

    @Override
    public void add(SubjectInfoBO subjectInfoBO) {
        if (CollectionUtils.isEmpty(subjectInfoBO.getOptionList())) {
            throw new RuntimeException("单选题选项不能为空");
        }
        Long subjectId = subjectInfoBO.getId();
        List<SubjectRadio> subjectRadioList = subjectInfoBO.getOptionList()
                .stream()
                .map(option -> {
                    SubjectRadio radio = SubjectRadioConverter.INSTANCE.convertBoToEntity(option);
                    radio.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
                    radio.setSubjectId(subjectId);
                    return radio;
                })
                .collect(Collectors.toList());
        subjectRadioService.batchInsert(subjectRadioList);
    }
}
