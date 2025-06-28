package com.cjl.subject.domain.service.Impl;

import com.alibaba.fastjson.JSON;
import com.cjl.subject.common.enums.IsDeletedFlagEnum;
import com.cjl.subject.domain.convert.SubjectCategoryConverter;
import com.cjl.subject.domain.convert.SubjectLabelConverter;
import com.cjl.subject.domain.entity.SubjectCategoryBO;
import com.cjl.subject.domain.entity.SubjectLabelBO;
import com.cjl.subject.domain.service.SubjectCategoryDomainService;
import com.cjl.subject.infra.basic.entity.SubjectCategory;
import com.cjl.subject.infra.basic.entity.SubjectMapping;
import com.cjl.subject.infra.basic.service.SubjectCategoryService;
import com.cjl.subject.infra.basic.service.SubjectLabelService;
import com.cjl.subject.infra.basic.service.SubjectMappingService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

    @Resource
    private SubjectMappingService subjectMappingService;

    @Resource
    private SubjectLabelService subjectLabelService;

    @Resource
    private ThreadPoolExecutor labelThreadPool;

    private Cache<String, String> localCache =
            CacheBuilder.newBuilder()
                    .maximumSize(5000)
                    .expireAfterWrite(10, TimeUnit.SECONDS)
                    .build();

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

    @Override
    public List<SubjectCategoryBO> queryCategoryAndLabel(SubjectCategoryBO subjectCategoryBO) {
        Long categoryId = subjectCategoryBO.getId();
        String cacheKey = "categoryAndLabel." + categoryId;
        String content = localCache.getIfPresent(cacheKey);
        List<SubjectCategoryBO> boList;
        if (StringUtils.isBlank(content)) {
            boList = doQueryCategoryAndLabel(categoryId);
            localCache.put(cacheKey, JSON.toJSONString(boList));
        } else {
            boList = JSON.parseArray(content, SubjectCategoryBO.class);
        }
        return boList;
    }

    private List<SubjectCategoryBO> doQueryCategoryAndLabel(Long categoryId) {
        //查找所以分类信息
        SubjectCategory subjectCategory = SubjectCategory.builder()
                .parentId(categoryId)
                .isDeleted(IsDeletedFlagEnum.UN_DELETED.getCode())
                .build();
        List<SubjectCategory> subjectCategoryList = subjectCategoryService.queryCategory(subjectCategory);
        List<SubjectCategoryBO> boList = SubjectCategoryConverter.INSTANCE.convertCategoryListToBoList(subjectCategoryList);
        Map<Long, List<SubjectLabelBO>> resultMap = new HashMap<>();
        List<CompletableFuture<Map<Long, List<SubjectLabelBO>>>> completableFutureList = boList.stream()
                .map(bo -> CompletableFuture.supplyAsync(() -> getLabelByCategoryId(bo.getId()), labelThreadPool))
                .collect(Collectors.toList());
        completableFutureList.forEach(future -> {
            try {
                Map<Long, List<SubjectLabelBO>> map = future.get();
                if (!CollectionUtils.isEmpty(map)) {
                    resultMap.putAll(map);
                }
            } catch (Exception e) {
                log.error("获取标签异常", e);
            }
        });
        boList.forEach(bo -> {
            bo.setSubjectLabelBOList(resultMap.get(bo.getId()));
        });
        return boList;
    }

    /**
     * 根据分类id查询标签
     */
    private Map<Long, List<SubjectLabelBO>> getLabelByCategoryId(Long categoryId) {
        //查询分类下的标签
        SubjectMapping subjectMapping = new SubjectMapping();
        subjectMapping.setCategoryId(categoryId);
        List<SubjectMapping> subjectMappingList = subjectMappingService.queryDistinctLabelId(subjectMapping);
        List<Long> labelIds = subjectMappingList.stream().map(SubjectMapping::getLabelId).collect(Collectors.toList());
        Map<Long, List<SubjectLabelBO>> resultMap = new HashMap<>();
        resultMap.put(categoryId, SubjectLabelConverter.INSTANCE.convertListEntityToBoList(subjectLabelService.batchQueryByIds(labelIds)));
        return resultMap;
    }
}
