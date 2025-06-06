package com.cjl.subject.domain.convert;

import com.cjl.subject.domain.entity.SubjectInfoBO;
import com.cjl.subject.infra.basic.entity.SubjectInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-04-8:50
 * @Description SubjectInfo与SubjectInfoBO相互转换
 */
@Mapper
public interface SubjectInfoConverter {
    SubjectInfoConverter INSTANCE = Mappers.getMapper(SubjectInfoConverter.class);

    /**
     * SubjectInfoBO转换为SubjectInfo
     */
    SubjectInfo convertBoToEntity(SubjectInfoBO subjectInfoBO);

    List<SubjectInfoBO> convertListEntityToBo(List<SubjectInfo> subjectInfoList);
}
