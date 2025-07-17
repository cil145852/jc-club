package com.cjl.subject.domain.convert;

import com.cjl.subject.domain.entity.SubjectLikedBO;
import com.cjl.subject.infra.basic.entity.SubjectLiked;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-07-17-21:29
 * @Description
 */

@Mapper
public interface SubjectLikedBOConverter {

    SubjectLikedBOConverter INSTANCE = Mappers.getMapper(SubjectLikedBOConverter.class);

    SubjectLiked convertBOToEntity(SubjectLikedBO subjectLikedBO);

    List<SubjectLikedBO> convertListEntityToBO(List<SubjectLiked> subjectLikedList);

}
