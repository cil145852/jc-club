package com.cjl.auth.domain.service.impl;

import com.cjl.auth.common.enums.AuthUserStatusEnum;
import com.cjl.auth.common.enums.IsDeletedFlagEnum;
import com.cjl.auth.domain.convert.AuthUserBOConverter;
import com.cjl.auth.domain.entity.AuthUserBO;
import com.cjl.auth.domain.service.AuthUserDomainService;
import com.cjl.auth.infra.basic.entity.AuthUser;
import com.cjl.auth.infra.basic.service.AuthUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    @Override
    public Boolean register(AuthUserBO authUserBO) {
        AuthUser authUser = AuthUserBOConverter.INSTANCE.convertBOToEntity(authUserBO);
        authUser.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        authUser.setStatus(AuthUserStatusEnum.OPEN.getCode());
        Integer count = authUserService.insert(authUser);
        return count  > 0;
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
