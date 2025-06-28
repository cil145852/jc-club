package com.cjl.auth.application.convert;

import com.cjl.auth.domain.entity.AuthUserBO;
import com.cjl.auth.entity.AuthUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-12-20:55
 * @Description
 */
@Mapper
public interface AuthUserDTOConverter {
    AuthUserDTOConverter INSTANCE = Mappers.getMapper(AuthUserDTOConverter.class);

    AuthUserBO convertDTOToBO(AuthUserDTO authUserDTO);

    AuthUserDTO convertBOToDTO(AuthUserBO authUserBO);
}
