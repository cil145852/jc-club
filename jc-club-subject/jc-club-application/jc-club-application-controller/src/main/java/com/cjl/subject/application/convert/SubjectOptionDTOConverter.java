package com.cjl.subject.application.convert;

import com.cjl.subject.application.dto.SubjectOptionDTO;
import com.cjl.subject.domain.entity.SubjectOptionBO;
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
public interface SubjectOptionDTOConverter {
    SubjectOptionDTOConverter INSTANCE = Mappers.getMapper(SubjectOptionDTOConverter.class);

    SubjectOptionBO convertDtoToBo(SubjectOptionDTO subjectOptionDTO);

    List<SubjectOptionBO> convertListDtoToBo(List<SubjectOptionDTO> subjectOptionDTO);
}
