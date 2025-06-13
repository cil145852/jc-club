package com.cjl.auth.domain.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.cjl.auth.common.enums.AuthUserStatusEnum;
import com.cjl.auth.common.enums.IsDeletedFlagEnum;
import com.cjl.auth.domain.constants.AuthConstant;
import com.cjl.auth.domain.convert.AuthUserBOConverter;
import com.cjl.auth.domain.entity.AuthUserBO;
import com.cjl.auth.domain.service.AuthUserDomainService;
import com.cjl.auth.infra.basic.entity.AuthRole;
import com.cjl.auth.infra.basic.entity.AuthUser;
import com.cjl.auth.infra.basic.entity.AuthUserRole;
import com.cjl.auth.infra.basic.service.AuthRoleService;
import com.cjl.auth.infra.basic.service.AuthUserRoleService;
import com.cjl.auth.infra.basic.service.AuthUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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

    private final String SALT = "cjl225714";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean register(AuthUserBO authUserBO) {
        AuthUser authUser = AuthUserBOConverter.INSTANCE.convertBOToEntity(authUserBO);
        authUser.setPassword(SaSecureUtil.md5BySalt(authUser.getPassword(), SALT));
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
}
