package com.cjl.subject.domain.service.Impl;

import com.cjl.subject.domain.convert.SubjectCategoryConverter;
import com.cjl.subject.domain.entity.SubjectCategoryBO;
import com.cjl.subject.domain.service.SubjectCategoryDomainService;
import com.cjl.subject.infra.basic.entity.SubjectCategory;
import com.cjl.subject.infra.basic.service.SubjectCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-01-20:54
 * @Description
 */
@Service
public class SubjectCategoryDomainServiceImpl implements SubjectCategoryDomainService {
    @Resource
    private SubjectCategoryService subjectCategoryService;

    @Override
    public void add(SubjectCategoryBO subjectCategoryBO) {
        SubjectCategory subjectCategory = SubjectCategoryConverter.INSTANCE.convertBoToCategory(subjectCategoryBO);
        subjectCategoryService.insert(subjectCategory);
    }
}
