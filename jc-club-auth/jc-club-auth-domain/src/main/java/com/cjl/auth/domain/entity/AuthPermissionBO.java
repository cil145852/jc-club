package com.cjl.auth.domain.entity;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 权限信息表(AuthPermission)实体类
 *
 * @author liang
 * @since 2025-06-13 20:010:53
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthPermissionBO implements Serializable {
    @Serialization
    private static final long serialVersionUID = 7070093605082262942L;
/**
     * 主键
     */
    private Long id;
/**
     * 权限名称
     */
    private String name;
/**
     * 父级权限id
     */
    private Long parentId;
/**
     * 权限类型
     */
    private Integer type;
/**
     * 菜单路由
     */
    private String menuUrl;
/**
     * 展示状态 0展示 1隐藏
     */
    private Integer show;
/**
     * 图标
     */
    private String icon;
/**
     * 权限标识
     */
    private String permissionKey;
/**
     * 状态 0启用 1禁用
     */
    private Integer status;
}

