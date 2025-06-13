package com.cjl.auth.domain.entity;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-13-11:34
 * @Description 角色DTO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRoleBO implements Serializable {
    @Serialization
    private static final long serialVersionUID = 6799189039500041814L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色唯一标识
     */
    private String roleKey;
}
