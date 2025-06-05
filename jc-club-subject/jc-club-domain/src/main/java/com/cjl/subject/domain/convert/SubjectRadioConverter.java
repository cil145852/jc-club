package com.cjl.subject.domain.convert;

import com.cjl.subject.domain.entity.SubjectOptionBO;
import com.cjl.subject.infra.basic.entity.SubjectRadio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-04-8:50
 * @Description SubjectRadio与SubjectOptionBo相互转换
 */
@Mapper
public interface SubjectRadioConverter {
    SubjectRadioConverter INSTANCE = Mappers.getMapper(SubjectRadioConverter.class);

    SubjectRadio convertBoToEntity(SubjectOptionBO subjectOptionBO);
}
