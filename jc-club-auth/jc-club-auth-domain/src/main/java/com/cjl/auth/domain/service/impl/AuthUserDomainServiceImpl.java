package com.cjl.auth.domain.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.cjl.auth.common.enums.AuthUserStatusEnum;
import com.cjl.auth.common.enums.IsDeletedFlagEnum;
import com.cjl.auth.domain.constants.AuthConstant;
import com.cjl.auth.domain.convert.AuthUserBOConverter;
import com.cjl.auth.domain.entity.AuthUserBO;
import com.cjl.auth.domain.redis.RedisUtil;
import com.cjl.auth.domain.service.AuthUserDomainService;
import com.cjl.auth.infra.basic.entity.*;
import com.cjl.auth.infra.basic.service.*;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-12-21:02
 * @Description
 */
@Slf4j
@Service
public class AuthUserDomainServiceImpl implements AuthUserDomainService {

    @Resource
    private AuthUserService authUserService;

    @Resource
    private AuthUserRoleService authUserRoleService;

    @Resource
    private AuthRoleService authRoleService;

    @Resource
    private AuthRolePermissionService authRolePermissionService;

    @Resource
    private AuthPermissionService authPermissionService;

    @Resource
    private RedisUtil redisUtil;

    private final String AUTH_PERMISSION_PREFIX = "auth.permission";

    private final String AUTH_ROLE_PREFIX = "auth.role";

    private final String SALT = "cjl225714";

    private static final String LOGIN_PREFIX = "loginCode";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean register(AuthUserBO authUserBO) {
        AuthUser authUser = AuthUserBOConverter.INSTANCE.convertBOToEntity(authUserBO);
        if (authUser.getPassword() != null) {
            authUser.setPassword(SaSecureUtil.md5BySalt(authUser.getPassword(), SALT));
        }
        authUser.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        authUser.setStatus(AuthUserStatusEnum.OPEN.getCode());
        Integer count = authUserService.insert(authUser);
        // 建立用户和角色关联
        AuthRole authRole = AuthRole.builder()
                .roleKey(AuthConstant.NORMAL_USER)
                .build();
        authRole = authRoleService.query(authRole);
        Long roleId = authRole.getId();
        Long userId = authUser.getId();
        authUserRoleService.insert(AuthUserRole.builder()
                .userId(userId)
                .roleId(roleId)
                .isDeleted(IsDeletedFlagEnum.UN_DELETED.getCode())
                .build());
        String roleKey = redisUtil.buildKey(AUTH_ROLE_PREFIX, authUser.getUsername());
        List<AuthRole> roleList = new ArrayList<>();
        roleList.add(authRole);
        redisUtil.set(roleKey, new Gson().toJson(roleList));
        List<AuthRolePermission> rolePermissionList = authRolePermissionService.queryByRoleIds(
                roleList.stream()
                .map(AuthRole::getId)
                .collect(Collectors.toList())
        );
        List<Long> permissionIds = rolePermissionList.stream()
                .map(AuthRolePermission::getPermissionId)
                .collect(Collectors.toList());
        List<AuthPermission> permissionList = authPermissionService.queryByIds(permissionIds);
        String permissionKey = redisUtil.buildKey(AUTH_PERMISSION_PREFIX, authUser.getUsername());
        redisUtil.set(permissionKey, new Gson().toJson(permissionList));
        return count > 0;
    }

    @Override
    public Boolean update(AuthUserBO authUserBO) {
        AuthUser authUser = AuthUserBOConverter.INSTANCE.convertBOToEntity(authUserBO);
        Integer count = authUserService.update(authUser);
        return count > 0;
    }

    @Override
    public Boolean delete(AuthUserBO authUserBO) {
        AuthUser authUser = AuthUser
                .builder()
                .id(authUserBO.getId())
                .isDeleted(IsDeletedFlagEnum.DELETED.getCode())
                .build();
        Integer count = authUserService.update(authUser);
        return count > 0;
    }

    @Override
    public SaTokenInfo doLogin(String verifyCode) {
        String key = redisUtil.buildKey(LOGIN_PREFIX, verifyCode);
        String openId = redisUtil.get(key);
        if (StringUtils.isBlank(openId)) {
            return null;
        }
        AuthUserBO authUserBO = AuthUserBO.builder()
                .username(openId)
                .password("123456")
                .build();
        /**
         * 注册用户
         * 非事物方法内部调用事物方法会导致事物失效
         * 由于调用时的参数authUserBO是自己创建的,所以不会出现问题,可以忽略事物失效
         */
        this.register(authUserBO);
        StpUtil.login(openId);
        return StpUtil.getTokenInfo();
    }
}
