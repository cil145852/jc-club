package com.cjl.subject.domain.convert;

import com.cjl.subject.domain.entity.SubjectLabelBO;
import com.cjl.subject.infra.basic.entity.SubjectLabel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-02-20:25
 * @Description SubjectCategory与SubjectCategoryBO相互转换
 */
@Mapper
public interface SubjectLabelConverter {
    SubjectLabelConverter INSTANCE = Mappers.getMapper(SubjectLabelConverter.class);

    SubjectLabel convertBoToLabel(SubjectLabelBO subjectLabelBO);

    List<SubjectLabelBO> convertLabelListToBoList(List<SubjectLabel> subjectLabelList);
}
