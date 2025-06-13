package com.cjl.auth.application.controller;

import com.cjl.auth.application.convert.AuthPermissionDTOConverter;
import com.cjl.auth.application.dto.AuthPermissionDTO;
import com.cjl.auth.common.entity.Result;
import com.cjl.auth.domain.entity.AuthPermissionBO;
import com.cjl.auth.domain.service.AuthPermissionDomainService;
import com.google.common.base.Preconditions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-13-20:09
 * @Description
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Resource
    private AuthPermissionDomainService authPermissionDomainService;

    /**
     * 添加角色
     */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody AuthPermissionDTO authPermissionDTO) {
        AuthPermissionBO authPermissionBO = AuthPermissionDTOConverter.INSTANCE.convertDTOToBO(authPermissionDTO);
        return Result.ok(authPermissionDomainService.add(authPermissionBO));
    }

    /**
     * 修改权限
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody AuthPermissionDTO authPermissionDTO) {
        Preconditions.checkNotNull(authPermissionDTO.getId(), "角色ID不能为空");
        AuthPermissionBO authPermissionBO = AuthPermissionDTOConverter.INSTANCE.convertDTOToBO(authPermissionDTO);
        return Result.ok(authPermissionDomainService.update(authPermissionBO));
    }

    /**
     * 删除权限
     */
    @PostMapping("/delete")
    public Result<Boolean> delete(@RequestBody AuthPermissionDTO authPermissionDTO) {
        Preconditions.checkNotNull(authPermissionDTO.getId(), "角色ID不能为空");
        AuthPermissionBO authPermissionBO = AuthPermissionDTOConverter.INSTANCE.convertDTOToBO(authPermissionDTO);
        return Result.ok(authPermissionDomainService.delete(authPermissionBO));
    }

    /**
     * 权限启用禁用
     */
    @PostMapping("/changeStatus")
    public Result<Boolean> changeStatus(@RequestBody AuthPermissionDTO authPermissionDTO) {
        Preconditions.checkNotNull(authPermissionDTO.getStatus(), "权限状态不能为空");
        AuthPermissionBO authPermissionBO = AuthPermissionDTOConverter.INSTANCE.convertDTOToBO(authPermissionDTO);
        return Result.ok(authPermissionDomainService.update(authPermissionBO));
    }
}
