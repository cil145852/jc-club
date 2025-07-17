package com.cjl.subject.infra.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cjl.subject.infra.basic.entity.SubjectLiked;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-07-17-17:08
 * @Description
 */

@Repository
public interface SubjectLikedDao extends BaseMapper<SubjectLiked> {

    int insertBatch(@Param("entities") List<SubjectLiked> entities);

    int countByCondition(SubjectLiked subjectLiked);

    List<SubjectLiked> queryPage(@Param("entity") SubjectLiked subjectLiked,
                                 @Param("start") int start,
                                 @Param("pageSize") Integer pageSize);

    void batchInsertOrUpdate(@Param("entities") List<SubjectLiked> subjectLikedList);

}
