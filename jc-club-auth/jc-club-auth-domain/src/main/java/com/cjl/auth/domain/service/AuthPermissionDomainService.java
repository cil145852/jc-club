package com.cjl.auth.domain.service;

import com.cjl.auth.domain.entity.AuthPermissionBO;

import java.util.List;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-13-20:17
 * @Description
 */

public interface AuthPermissionDomainService {
    Boolean add(AuthPermissionBO authPermissionBO);

    Boolean update(AuthPermissionBO authPermissionBO);

    Boolean delete(AuthPermissionBO authPermissionBO);

    List<String> getPermission(String username);
}
