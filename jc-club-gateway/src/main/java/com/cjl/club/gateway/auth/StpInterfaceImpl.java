package com.cjl.club.gateway.auth;

import cn.dev33.satoken.stp.StpInterface;
import com.cjl.club.gateway.entity.AuthPermission;
import com.cjl.club.gateway.redis.RedisUtil;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-11-21:14
 * @Description 自定义权限验证接口扩展
 */
@Component
public class StpInterfaceImpl implements StpInterface {
    @Resource
    private RedisUtil redisUtil;

    private final String AUTH_PERMISSION_PREFIX = "auth.permission";
    private final String AUTH_ROLE_PREFIX = "auth.role";

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        String authValue = getAuthValue(AUTH_PERMISSION_PREFIX, loginId.toString());
        if (!StringUtils.hasText(authValue)) {
            return Collections.emptyList();
        }
        List<AuthPermission> permissionList = new Gson().fromJson(authValue, new TypeToken<List<AuthPermission>>() {
        }.getType());
        return permissionList.stream().map(AuthPermission::getPermissionKey).collect(Collectors.toList());
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        String authValue = getAuthValue(AUTH_ROLE_PREFIX, loginId.toString());
        if (!StringUtils.hasText(authValue)) {
            return Collections.emptyList();
        }
        List<AuthPermission> permissionList = new Gson().fromJson(authValue, new TypeToken<List<AuthPermission>>() {
        }.getType());
        return permissionList.stream().map(AuthPermission::getPermissionKey).collect(Collectors.toList());
    }

    private String getAuthValue(String prefix, String loginId) {
        String authKey = redisUtil.buildKey(prefix, loginId);
        return redisUtil.get(authKey);
    }
}
