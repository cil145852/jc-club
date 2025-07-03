package com.cjl.subject.domain.util;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-07-03-14:55
 * @Description
 */
@Component
public class CacheUtil<V> {
   private Cache<String, String> localCache =
           CacheBuilder.newBuilder()
                   .maximumSize(5000)
                   .expireAfterWrite(20, TimeUnit.SECONDS)
                   .build();

   public List<V> getResult(String cacheKey, Class<V> clazz,
                            Function<String, List<V>> function) {
      List<V> resultList;
      String cacheValue = localCache.getIfPresent(cacheKey);
      if (!StringUtils.isBlank(cacheValue)) {
         resultList = JSON.parseArray(cacheValue, clazz);
      } else {
         resultList = function.apply(cacheKey);
          if (!CollectionUtils.isEmpty(resultList)) {
              localCache.put(cacheKey, JSON.toJSONString(resultList));
          }
      }
      return resultList;
   }
}
