package com.cjl.auth.application.controller;

import com.cjl.auth.application.convert.AuthUserDTOConverter;
import com.cjl.auth.application.dto.AuthUserDTO;
import com.cjl.auth.common.entity.Result;
import com.cjl.auth.domain.entity.AuthUserBO;
import com.cjl.auth.domain.service.AuthUserDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-12-20:41
 * @Description
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private AuthUserDomainService authUserDomainService;

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result<Boolean> register(@RequestBody AuthUserDTO authUserDTO) {
        AuthUserBO authUserBO = AuthUserDTOConverter.INSTANCE.convertDTOToBO(authUserDTO);
        return Result.ok(authUserDomainService.register(authUserBO));
    }

    /**
     * 修改用户信息
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody AuthUserDTO authUserDTO) {
        AuthUserBO authUserBO = AuthUserDTOConverter.INSTANCE.convertDTOToBO(authUserDTO);
        return Result.ok(authUserDomainService.update(authUserBO));
    }

    /**
     * 删除用户信息
     */
    @PostMapping("/delete")
    public Result<Boolean> delete(@RequestBody AuthUserDTO authUserDTO) {
        AuthUserBO authUserBO = AuthUserDTOConverter.INSTANCE.convertDTOToBO(authUserDTO);
        return Result.ok(authUserDomainService.delete(authUserBO));
    }
}
