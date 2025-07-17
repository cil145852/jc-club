package com.cjl.subject.infra.rpc;

import com.cjl.auth.api.UserFeignService;
import com.cjl.auth.entity.AuthUserDTO;
import com.cjl.auth.entity.Result;
import com.cjl.subject.infra.entity.UserInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-28-15:25
 * @Description
 */

@Component
public class UserRPC {
    @Resource
    private UserFeignService userFeignService;

    public UserInfo getUserInfo(String username) {
        AuthUserDTO authUserDTO = new AuthUserDTO();
        authUserDTO.setUsername(username);
        Result<AuthUserDTO> result = userFeignService.getUserInfo(authUserDTO);
        UserInfo userInfo = new UserInfo();
        if (!result.getSuccess()) {
            return userInfo;
        }
        AuthUserDTO userDTO = result.getData();
        userInfo.setNickname(userDTO.getNickname());
        userInfo.setUsername(userDTO.getUsername());
        userInfo.setAvatar(userDTO.getAvatar());
        return userInfo;
    }
}
