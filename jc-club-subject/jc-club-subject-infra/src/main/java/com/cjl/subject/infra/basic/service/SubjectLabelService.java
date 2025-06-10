package com.cjl.subject.infra.basic.service;

import com.cjl.subject.infra.basic.entity.SubjectLabel;

import java.util.List;

/**
 * 题目标签表(SubjectLabel)表服务接口
 *
 * @author makejava
 * @since 2025-06-03 16:48:07
 */
public interface SubjectLabelService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SubjectLabel queryById(Long id);

    /**
     * 新增数据
     *
     * @param subjectLabel 实例对象
     * @return 实例对象
     */
    Integer insert(SubjectLabel subjectLabel);

    /**
     * 修改数据
     *
     * @param subjectLabel 实例对象
     * @return 实例对象
     */
    Integer update(SubjectLabel subjectLabel);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 通过主键批量查询数据
     *
     * @param labelIds 主键
     * @return 是否成功
     */
    List<SubjectLabel> batchQueryByIds(List<Long> labelIds);

    /**
     * 根据条件查询数据
     *
     * @param subjectLabel 主键
     * @return 是否成功
     */
    List<SubjectLabel> query(SubjectLabel subjectLabel);
}
