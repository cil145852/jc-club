package com.cjl.auth.application.dto;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色权限关联表(AuthRolePermission)实体类
 *
 * @author liang
 * @since 2025-06-13 21:08:50
 */
@Data
public class AuthRolePermissionDTO implements Serializable {
    @Serialization
    private static final long serialVersionUID = -2275824935423758172L;
/**
     * 主键
     */
    private Long id;
/**
     * 角色id
     */
    private Long roleId;
/**
     * 权限id
     */
    private Long permissionId;

    private List<Long> permissionIdList;
}

