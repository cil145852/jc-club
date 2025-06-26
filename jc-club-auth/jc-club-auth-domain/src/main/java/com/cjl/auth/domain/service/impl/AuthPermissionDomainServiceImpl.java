package com.cjl.auth.domain.service.impl;

import com.cjl.auth.common.enums.IsDeletedFlagEnum;
import com.cjl.auth.domain.convert.AuthPermissionBOConverter;
import com.cjl.auth.domain.entity.AuthPermissionBO;
import com.cjl.auth.domain.redis.RedisUtil;
import com.cjl.auth.domain.service.AuthPermissionDomainService;
import com.cjl.auth.infra.basic.entity.AuthPermission;
import com.cjl.auth.infra.basic.service.AuthPermissionService;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-13-20:18
 * @Description
 */
@Service
@Slf4j
public class AuthPermissionDomainServiceImpl implements AuthPermissionDomainService {
    @Resource
    private AuthPermissionService authPermissionService;

    @Resource
    private RedisUtil redisUtil;

    private final String AUTH_PERMISSION_PREFIX = "auth.permission";

    @Override
    public Boolean add(AuthPermissionBO authPermissionBO) {
        AuthPermission authPermission = AuthPermissionBOConverter.INSTANCE.convertBOToEntity(authPermissionBO);
        authPermission.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        Integer count = authPermissionService.insert(authPermission);
        return count > 0;
    }

    @Override
    public Boolean update(AuthPermissionBO authPermissionBO) {
        AuthPermission authPermission = AuthPermissionBOConverter.INSTANCE.convertBOToEntity(authPermissionBO);
        Integer count = authPermissionService.update(authPermission);
        return count > 0;
    }

    @Override
    public Boolean delete(AuthPermissionBO authPermissionBO) {
        AuthPermission authPermission = new AuthPermission();
        authPermission.setId(authPermissionBO.getId());
        authPermission.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        Integer count = authPermissionService.update(authPermission);
        return count > 0;
    }

    @Override
    public List<String> getPermission(String username) {
        String key = redisUtil.buildKey(AUTH_PERMISSION_PREFIX, username);
        String permissionValue = redisUtil.get(key);
        if (!StringUtils.hasText(permissionValue)) {
            return Collections.emptyList();
        }
        List<AuthPermission> authPermissionList= new Gson().fromJson(permissionValue, new TypeToken<List<AuthPermission>>() {
        }.getType());
        return authPermissionList.stream().map(AuthPermission::getPermissionKey).collect(Collectors.toList());
    }
}
