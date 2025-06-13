package com.cjl.auth.domain.service;

import com.cjl.auth.domain.entity.AuthRoleBO;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-13-14:12
 * @Description
 */

public interface AuthRoleDomainService {
    Boolean add(AuthRoleBO authRoleBO);

    Boolean update(AuthRoleBO authRoleBO);

    Boolean delete(AuthRoleBO authRoleBO);
}
