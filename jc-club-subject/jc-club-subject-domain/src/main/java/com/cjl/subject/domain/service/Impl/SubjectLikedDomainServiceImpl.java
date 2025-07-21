package com.cjl.subject.domain.service.Impl;

import com.alibaba.fastjson.JSON;
import com.cjl.subject.common.entity.PageResult;
import com.cjl.subject.common.enums.SubjectLikedStatusEnum;
import com.cjl.subject.domain.entity.SubjectLikedBO;
import com.cjl.subject.domain.redis.RedisUtil;
import com.cjl.subject.domain.service.SubjectLikedDomainService;
import com.cjl.subject.infra.basic.entity.SubjectLiked;
import com.cjl.subject.infra.basic.service.SubjectLikedService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public Boolean isLiked(Long subjectId, String userId) {
        String key = SUBJECT_LIKED_DETAIL_KEY + "." + subjectId + "." + userId;
        return redisUtil.exist(key);
    }

    @Override
    public Integer getLikedCount(Long subjectId) {
        String countKey = SUBJECT_LIKED_COUNT_KEY + "." + subjectId;
        Integer count = redisUtil.getInt(countKey);
        return count == null || count <= 0 ? 0 : count;
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
        Map<Object, Object> subjectLikedMap = redisUtil.getHashAndDelete(SUBJECT_LIKED_KEY);
        if (log.isInfoEnabled()) {
            log.info("syncLiked.subjectLikedMap:{}", JSON.toJSONString(subjectLikedMap));
        }
        if (MapUtils.isEmpty(subjectLikedMap)) {
            return;
        }
        List<SubjectLiked> subjectLikedList = new ArrayList<>();
        subjectLikedMap.forEach((key, value) -> {
            SubjectLiked subjectLiked = new SubjectLiked();
            String[] keyArr = key.toString().split(":");
            if (keyArr.length != 2) {
                throw new RuntimeException("Invalid subjectLiked key: " + key);
            }
            String subjectId = keyArr[0];
            String likedUser = keyArr[1];
            subjectLiked.setLikeUserId(likedUser);
            subjectLiked.setSubjectId(Long.valueOf(subjectId));
            subjectLiked.setStatus(Integer.valueOf(value.toString()));
            subjectLikedList.add(subjectLiked);
        });
        subjectLikedService.batchInsert(subjectLikedList);
    }

    @Override
    public PageResult<SubjectLikedBO> getSubjectLikedPage(SubjectLikedBO subjectLikedBO) {
        return null;
    }
}
