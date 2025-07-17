package com.cjl.subject.domain.convert;

import com.cjl.subject.domain.entity.SubjectInfoBO;
import com.cjl.subject.infra.basic.entity.SubjectInfo;
import com.cjl.subject.infra.basic.entity.SubjectInfoEs;
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
    SubjectInfo convertBOToEntity(SubjectInfoBO subjectInfoBO);

    List<SubjectInfoBO> convertListEntityToBO(List<SubjectInfo> subjectInfoList);

    SubjectInfoBO convertEntityToBO(SubjectInfo subjectInfo);

    SubjectInfoEs convertBOToEsEntity(SubjectInfoBO subjectInfoBO);

    SubjectInfoBO convertEsEntityToBO(SubjectInfoEs subjectInfoEs);

    List<SubjectInfoBO> convertListEsEntityToBO(List<SubjectInfoEs> result);
}
