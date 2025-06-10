package com.cjl.subject.application.convert;

import com.cjl.subject.application.dto.SubjectCategoryDTO;
import com.cjl.subject.domain.entity.SubjectCategoryBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-02-20:25
 * @Description
 */
@Mapper
public interface SubjectCategoryDTOConverter {
    SubjectCategoryDTOConverter INSTANCE = Mappers.getMapper(SubjectCategoryDTOConverter.class);

    SubjectCategoryBO convertDtoToBo(SubjectCategoryDTO subjectCategoryDTO);

    List<SubjectCategoryDTO> convertBoListToDtoList(List<SubjectCategoryBO> subjectCategoryBOList);
}
