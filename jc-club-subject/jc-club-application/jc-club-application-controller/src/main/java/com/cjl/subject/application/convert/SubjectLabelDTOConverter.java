package com.cjl.subject.application.convert;

import com.cjl.subject.application.dto.SubjectLabelDTO;
import com.cjl.subject.domain.entity.SubjectLabelBO;
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
public interface SubjectLabelDTOConverter {
    SubjectLabelDTOConverter INSTANCE = Mappers.getMapper(SubjectLabelDTOConverter.class);

    SubjectLabelBO convertDtoToBo(SubjectLabelDTO subjectLabelDTO);

    List<SubjectLabelDTO> convertBoListToDtoList(List<SubjectLabelBO> subjectLabelBOList);
}
