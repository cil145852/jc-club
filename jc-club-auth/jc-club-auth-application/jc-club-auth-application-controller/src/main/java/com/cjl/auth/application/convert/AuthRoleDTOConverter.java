package com.cjl.auth.application.convert;

import com.cjl.auth.application.dto.AuthRoleDTO;
import com.cjl.auth.domain.entity.AuthRoleBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-13-14:18
 * @Description
 */
@Mapper
public interface AuthRoleDTOConverter {
    AuthRoleDTOConverter INSTANCE = Mappers.getMapper(AuthRoleDTOConverter.class);
    AuthRoleBO convertDTOToBO(AuthRoleDTO authRoleDTO);
}
