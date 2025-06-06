package com.cjl.subject.infra.basic.service;

import com.cjl.subject.infra.basic.entity.SubjectBrief;

import java.util.List;

/**
 * 简答题信息表(SubjectBrief)表服务接口
 *
 * @author makejava
 * @since 2025-06-04 08:15:47
 */
public interface SubjectBriefService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SubjectBrief queryById(Long id);

    /**
     * 新增数据
     *
     * @param subjectBrief 实例对象
     * @return 实例对象
     */
    Integer insert(SubjectBrief subjectBrief);

    /**
     * 修改数据
     *
     * @param subjectBrief 实例对象
     * @return 实例对象
     */
    SubjectBrief update(SubjectBrief subjectBrief);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 根据条件查询多条数据
     *
     * @param subjectBrief 查询条件
     * @return 对象列表
     */
    List<SubjectBrief> query(SubjectBrief subjectBrief);
}
