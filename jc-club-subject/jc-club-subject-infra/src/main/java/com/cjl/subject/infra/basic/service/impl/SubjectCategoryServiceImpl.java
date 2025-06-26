package com.cjl.subject.infra.basic.service.impl;

import com.cjl.subject.infra.basic.entity.SubjectCategory;
import com.cjl.subject.infra.basic.mapper.SubjectCategoryDao;
import com.cjl.subject.infra.basic.service.SubjectCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 题目分类(SubjectCategory)表服务实现类
 *
 * @author makejava
 * @since 2025-06-01 16:59:01
 */
@Service("subjectCategoryService")
public class SubjectCategoryServiceImpl implements SubjectCategoryService {
    @Resource
    private SubjectCategoryDao subjectCategoryDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SubjectCategory queryById(Long id) {
        return this.subjectCategoryDao.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param subjectCategory 实例对象
     * @return 实例对象
     */
    @Override
    public SubjectCategory insert(SubjectCategory subjectCategory) {
        this.subjectCategoryDao.insert(subjectCategory);
        return subjectCategory;
    }

    /**
     * 修改数据
     *
     * @param subjectCategory 实例对象
     * @return 实例对象
     */
    @Override
    public Integer update(SubjectCategory subjectCategory) {
        return this.subjectCategoryDao.update(subjectCategory);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.subjectCategoryDao.deleteById(id) > 0;
    }

    /**
     * 查询所有一级分类
     * @return
     */
    @Override
    public List<SubjectCategory> queryCategory(SubjectCategory subjectCategory) {
        return subjectCategoryDao.queryCategory(subjectCategory);
    }

    @Override
    public Integer querySubjectCount(Long id) {
        return this.subjectCategoryDao.querySubjectCount(id);
    }
}
