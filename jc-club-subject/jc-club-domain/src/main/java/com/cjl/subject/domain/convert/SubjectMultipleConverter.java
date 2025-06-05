package com.cjl.subject.domain.convert;

import com.cjl.subject.domain.entity.SubjectOptionBO;
import com.cjl.subject.infra.basic.entity.SubjectMultiple;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-04-8:50
 * @Description
 */
@Mapper
public interface SubjectMultipleConverter {
    SubjectMultipleConverter INSTANCE = Mappers.getMapper(SubjectMultipleConverter.class);

    SubjectMultiple convertBoToEntity(SubjectOptionBO subjectOptionBO);
}
