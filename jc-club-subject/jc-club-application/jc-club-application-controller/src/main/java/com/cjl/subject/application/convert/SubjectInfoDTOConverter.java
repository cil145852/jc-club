package com.cjl.subject.application.convert;

import com.cjl.subject.application.dto.SubjectInfoDTO;
import com.cjl.subject.domain.entity.SubjectInfoBO;
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
public interface SubjectInfoDTOConverter {
    SubjectInfoDTOConverter INSTANCE = Mappers.getMapper(SubjectInfoDTOConverter.class);

    SubjectInfoBO convertDtoToBo(SubjectInfoDTO subjectInfoDTO);

    List<SubjectInfoDTO> convertListBoToDto(List<SubjectInfoBO> subjectInfoBOList);
}
