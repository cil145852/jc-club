package com.cjl.auth.domain.service.impl;

import com.cjl.auth.common.enums.IsDeletedFlagEnum;
import com.cjl.auth.domain.entity.AuthRolePermissionBO;
import com.cjl.auth.domain.service.AuthRolePermissionDomainService;
import com.cjl.auth.infra.basic.entity.AuthRolePermission;
import com.cjl.auth.infra.basic.service.AuthRolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-13-21:16
 * @Description
 */
@Service
@Slf4j
public class AuthRolePermissionDomainServiceImpl implements AuthRolePermissionDomainService {
    @Resource
    private AuthRolePermissionService authRolePermissionService;

    /**
     * 添加角色权限关系
     */
    @Override
    public Boolean add(AuthRolePermissionBO authRolePermissionBO) {
        Long roleId = authRolePermissionBO.getRoleId();
        List<AuthRolePermission> authRolePermissionList = new ArrayList<>();
        authRolePermissionBO.getPermissionIdList().forEach(permissionId -> {
            AuthRolePermission authRolePermission = new AuthRolePermission();
            authRolePermission.setRoleId(roleId);
            authRolePermission.setPermissionId(permissionId);
            authRolePermission.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
            authRolePermissionList.add(authRolePermission);
        });
        Integer count = authRolePermissionService.batchInsert(authRolePermissionList);
        return count > 0;
    }
}
