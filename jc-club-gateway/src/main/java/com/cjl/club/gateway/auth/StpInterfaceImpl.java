package com.cjl.club.gateway.auth;

import cn.dev33.satoken.stp.StpInterface;
import com.cjl.club.gateway.redis.RedisUtil;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

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
        // 返回此 loginId 拥有的权限列表
        return getAuthList(AUTH_PERMISSION_PREFIX, loginId.toString());
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 返回此 loginId 拥有的角色列表
        return getAuthList(AUTH_ROLE_PREFIX, loginId.toString());
    }

    private List<String> getAuthList(String prefix, String loginId) {
        String authKey = redisUtil.buildKey(prefix, loginId);
        String authValue = redisUtil.get(authKey);
        if (!StringUtils.hasText(authValue)) {
            return Collections.emptyList();
        }
        List<String> list = new Gson().fromJson(authValue, List.class);
        return list;
    }
}
