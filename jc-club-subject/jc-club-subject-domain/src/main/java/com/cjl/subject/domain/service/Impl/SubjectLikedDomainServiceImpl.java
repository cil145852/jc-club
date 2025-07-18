package com.cjl.subject.domain.service.Impl;

import com.cjl.subject.common.entity.PageResult;
import com.cjl.subject.common.enums.SubjectLikedStatusEnum;
import com.cjl.subject.domain.entity.SubjectLikedBO;
import com.cjl.subject.domain.redis.RedisUtil;
import com.cjl.subject.domain.service.SubjectLikedDomainService;
import com.cjl.subject.infra.basic.service.SubjectLikedService;
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
    private static final String SUBJECT_LIKED_KEY = "subject.liked";

    private static final String SUBJECT_LIKED_COUNT_KEY = "subject.liked.count";

    private static final String SUBJECT_LIKED_DETAIL_KEY = "subject.liked.detail";

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private SubjectLikedService subjectLikedService;
    @Override
    public void add(SubjectLikedBO subjectLikedBO) {
        Long subjectId = subjectLikedBO.getSubjectId();
        String likeUserId = subjectLikedBO.getLikeUserId();
        Integer status = subjectLikedBO.getStatus();
        String hashKey = subjectId + ":" + likeUserId;
        String countKey = SUBJECT_LIKED_COUNT_KEY + "." + subjectId;
        String detailKey = SUBJECT_LIKED_DETAIL_KEY + "." + subjectId + "." + likeUserId;
        redisUtil.putHash(SUBJECT_LIKED_KEY, hashKey, status);
        if (SubjectLikedStatusEnum.LIKED.getCode() == status) {
            redisUtil.increment(countKey, 1);
            redisUtil.set(detailKey, "1");
        } else {
            Integer count = redisUtil.getInt(countKey);
            if (count == null || count <= 0) {
                return;
            }
            redisUtil.increment(countKey, -1);
            redisUtil.del(detailKey);
        }
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
