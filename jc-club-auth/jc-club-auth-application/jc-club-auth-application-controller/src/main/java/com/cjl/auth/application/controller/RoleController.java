package com.cjl.auth.application.controller;

import com.cjl.auth.application.convert.AuthRoleDTOConverter;
import com.cjl.auth.application.dto.AuthRoleDTO;
import com.cjl.auth.domain.entity.AuthRoleBO;
import com.cjl.auth.domain.service.AuthRoleDomainService;
import com.cjl.auth.entity.Result;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-13-11:31
 * @Description
 */
@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController {
    @Resource
    private AuthRoleDomainService authRoleDomainService;

    /**
     * 添加角色
     */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody AuthRoleDTO authRoleDTO) {
        AuthRoleBO authRoleBO = AuthRoleDTOConverter.INSTANCE.convertDTOToBO(authRoleDTO);
        return Result.ok(authRoleDomainService.add(authRoleBO));
    }

    /**
     * 修改角色
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody AuthRoleDTO authRoleDTO) {
        Preconditions.checkNotNull(authRoleDTO.getId(), "角色ID不能为空");
        AuthRoleBO authRoleBO = AuthRoleDTOConverter.INSTANCE.convertDTOToBO(authRoleDTO);
        return Result.ok(authRoleDomainService.update(authRoleBO));
    }

    /**
     * 删除角色
     */
    @PostMapping("/delete")
    public Result<Boolean> delete(@RequestBody AuthRoleDTO authRoleDTO) {
        Preconditions.checkNotNull(authRoleDTO.getId(), "角色ID不能为空");
        AuthRoleBO authRoleBO = AuthRoleDTOConverter.INSTANCE.convertDTOToBO(authRoleDTO);
        return Result.ok(authRoleDomainService.delete(authRoleBO));
    }
}
