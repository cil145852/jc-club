package com.cjl.auth.application.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.cjl.auth.application.convert.AuthUserDTOConverter;
import com.cjl.auth.application.dto.AuthUserDTO;
import com.cjl.auth.common.entity.Result;
import com.cjl.auth.domain.entity.AuthUserBO;
import com.cjl.auth.domain.service.AuthUserDomainService;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    /**
     * 用户启用禁用
     */
    @PostMapping("/changeStatus")
    public Result<Boolean> changeStatus(@RequestBody AuthUserDTO authUserDTO) {
        Preconditions.checkNotNull(authUserDTO.getStatus(), "用户状态不能为空");
        AuthUserBO authUserBO = AuthUserDTOConverter.INSTANCE.convertDTOToBO(authUserDTO);
        return Result.ok(authUserDomainService.update(authUserBO));
    }

    /**
     * 用户登录
     */
    @GetMapping("/doLogin")
    public Result<SaTokenInfo> doLogin(@RequestParam("validCode") String verifyCode) {
        Preconditions.checkNotNull(verifyCode, "验证码不能为空");
        return Result.ok(authUserDomainService.doLogin(verifyCode));
    }

    /**
     * 用户退出
     */
    @GetMapping("/logOut")
    public Result logout(@RequestParam("userName") String username) {
        Preconditions.checkNotNull(username, "用户名不能为空");
        StpUtil.logout(username);
        return Result.ok();
    }

    /**
     * 获取用户信息
     */
    @PostMapping("/getUserInfo")
    public Result<AuthUserDTO> getUserInfo(@RequestBody AuthUserDTO authUserDTO) {
        AuthUserBO authUserBO = AuthUserDTOConverter.INSTANCE.convertDTOToBO(authUserDTO);
        authUserBO = authUserDomainService.getUserInfo(authUserBO);
        return Result.ok(AuthUserDTOConverter.INSTANCE.convertBOToDTO(authUserBO));
    }
}
