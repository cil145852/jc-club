package com.cjl.auth.application.controller;

import com.cjl.auth.application.convert.AuthRolePermissionDTOConverter;
import com.cjl.auth.application.dto.AuthRolePermissionDTO;
import com.cjl.auth.common.entity.Result;
import com.cjl.auth.domain.entity.AuthRolePermissionBO;
import com.cjl.auth.domain.service.AuthRolePermissionDomainService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-13-21:09
 * @Description
 */
@RestController
@RequestMapping("/rolePermission")
public class RolePermissionController {
    @Resource
    private AuthRolePermissionDomainService authRolePermissionDomainService;

    /**
     * 添加角色权限关系
     */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody AuthRolePermissionDTO authRolePermissionDTO) {
        AuthRolePermissionBO authRolePermissionBO = AuthRolePermissionDTOConverter.INSTANCE.convertDTOToBO(authRolePermissionDTO);
        return Result.ok(authRolePermissionDomainService.add(authRolePermissionBO));
    }
}
