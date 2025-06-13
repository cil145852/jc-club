package com.cjl.auth.application.convert;

import com.cjl.auth.application.dto.AuthRolePermissionDTO;
import com.cjl.auth.domain.entity.AuthRolePermissionBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-13-21:10
 * @Description
 */
@Mapper
public interface AuthRolePermissionDTOConverter {
    AuthRolePermissionDTOConverter INSTANCE = Mappers.getMapper(AuthRolePermissionDTOConverter.class);

    AuthRolePermissionBO convertDTOToBO(AuthRolePermissionDTO authRolePermissionDTO);

}
