package com.cjl.subject.domain.service.Impl;

import com.cjl.subject.common.enums.IsDeletedFlagEnum;
import com.cjl.subject.domain.convert.SubjectCategoryConverter;
import com.cjl.subject.domain.entity.SubjectCategoryBO;
import com.cjl.subject.domain.service.SubjectCategoryDomainService;
import com.cjl.subject.infra.basic.entity.SubjectCategory;
import com.cjl.subject.infra.basic.service.SubjectCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-01-20:54
 * @Description
 */
@Service
@Slf4j
public class SubjectCategoryDomainServiceImpl implements SubjectCategoryDomainService {
    @Resource
    private SubjectCategoryService subjectCategoryService;

    /**
     * 添加分类
     *
     * @param subjectCategoryBO
     */
    @Override
    public void add(SubjectCategoryBO subjectCategoryBO) {
        SubjectCategory subjectCategory = SubjectCategoryConverter.INSTANCE.convertBoToCategory(subjectCategoryBO);
        subjectCategory.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        subjectCategoryService.insert(subjectCategory);
    }

    /**
     * 根据条件查询分类
     *
     * @param subjectCategoryBO
     * @return
     */
    @Override
    public List<SubjectCategoryBO> queryCategory(SubjectCategoryBO subjectCategoryBO) {
        SubjectCategory subjectCategory = SubjectCategoryConverter.INSTANCE.convertBoToCategory(subjectCategoryBO);
        subjectCategory.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        List<SubjectCategory> subjectCategoryList = subjectCategoryService.queryCategory(subjectCategory);
        List<SubjectCategoryBO> boList = SubjectCategoryConverter.INSTANCE.convertCategoryListToBoList(subjectCategoryList);
        boList.forEach(bo -> {
            Integer subjectCount = subjectCategoryService.querySubjectCount(bo.getId());
            bo.setCount(subjectCount);
        });
        return boList;
    }

    /**
     * 修改分类
     *
     * @param subjectCategoryBO
     * @return
     */
    @Override
    public Boolean update(SubjectCategoryBO subjectCategoryBO) {
        SubjectCategory subjectCategory = SubjectCategoryConverter.INSTANCE.convertBoToCategory(subjectCategoryBO);
        Integer count = subjectCategoryService.update(subjectCategory);
        return count > 0;
    }

    /**
     * 删除分类
     *
     * @param subjectCategoryBO
     * @return
     */
    @Override
    public Boolean delete(SubjectCategoryBO subjectCategoryBO) {
        SubjectCategory subjectCategory = SubjectCategoryConverter.INSTANCE.convertBoToCategory(subjectCategoryBO);
        subjectCategory.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        Integer count = subjectCategoryService.update(subjectCategory);
        return count > 0;
    }
}
