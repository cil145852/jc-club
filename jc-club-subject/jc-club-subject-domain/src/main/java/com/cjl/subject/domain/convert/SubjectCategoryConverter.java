package com.cjl.subject.domain.convert;

import com.cjl.subject.domain.entity.SubjectCategoryBO;
import com.cjl.subject.infra.basic.entity.SubjectCategory;
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
public interface SubjectCategoryConverter {
    SubjectCategoryConverter INSTANCE = Mappers.getMapper(SubjectCategoryConverter.class);

    SubjectCategory convertBoToCategory(SubjectCategoryBO subjectCategoryBO);

    List<SubjectCategoryBO> convertCategoryListToBoList(List<SubjectCategory> subjectCategory);
}
