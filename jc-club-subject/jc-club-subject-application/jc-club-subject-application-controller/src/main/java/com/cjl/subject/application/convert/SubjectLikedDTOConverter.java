package com.cjl.subject.application.convert;

import com.cjl.subject.application.dto.SubjectLikedDTO;
import com.cjl.subject.domain.entity.SubjectLikedBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-07-17-21:34
 * @Description
 */

@Mapper
public interface SubjectLikedDTOConverter {

    SubjectLikedDTOConverter INSTANCE = Mappers.getMapper(SubjectLikedDTOConverter.class);

    SubjectLikedBO convertDTOToBO(SubjectLikedDTO subjectLikedDTO);

    List<SubjectLikedDTO> convertListBOToDTO(List<SubjectLikedBO> subjectLikedBOList);

}