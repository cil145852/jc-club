package com.cjl.auth.domain.convert;

import com.cjl.auth.domain.entity.AuthRolePermissionBO;
import com.cjl.auth.infra.basic.entity.AuthRolePermission;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-13-21:12
 * @Description
 */
@Mapper
public interface AuthRolePermissionBOConverter {
    AuthRolePermissionBOConverter INSTANCE = Mappers.getMapper(AuthRolePermissionBOConverter.class);

    AuthRolePermission convertBOToEntity(AuthRolePermissionBO authRolePermissionBO);
}
