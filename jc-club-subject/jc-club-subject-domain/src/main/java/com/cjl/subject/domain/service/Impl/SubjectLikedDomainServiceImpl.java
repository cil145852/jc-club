package com.cjl.subject.domain.service.Impl;

import com.cjl.subject.common.entity.PageResult;
import com.cjl.subject.domain.entity.SubjectLikedBO;
import com.cjl.subject.domain.redis.RedisUtil;
import com.cjl.subject.domain.service.SubjectLikedDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-07-17-21:28
 * @Description
 */

@Service
@Slf4j
public class SubjectLikedDomainServiceImpl implements SubjectLikedDomainService {

    @Resource
    private RedisUtil redisUtil;
    @Override
    public void add(SubjectLikedBO subjectLikedBO) {

    }

    @Override
    public Boolean isLiked(String subjectId, String userId) {
        return null;
    }

    @Override
    public Integer getLikedCount(String subjectId) {
        return 0;
    }

    @Override
    public Boolean update(SubjectLikedBO subjectLikedBO) {
        return null;
    }

    @Override
    public Boolean delete(SubjectLikedBO subjectLikedBO) {
        return null;
    }

    @Override
    public void syncLiked() {

    }

    @Override
    public PageResult<SubjectLikedBO> getSubjectLikedPage(SubjectLikedBO subjectLikedBO) {
        return null;
    }
}
