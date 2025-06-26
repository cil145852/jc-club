package com.cjl.auth.domain.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.cjl.auth.domain.entity.AuthUserBO;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-12-21:02
 * @Description
 */
public interface AuthUserDomainService {
    Boolean register(AuthUserBO authUserBO);

    Boolean update(AuthUserBO authUserBO);

    Boolean delete(AuthUserBO authUserBO);

    SaTokenInfo doLogin(String verifyCode);
}
