package com.cjl.club.gateway.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限信息表(AuthPermission)实体类
 *
 * @author makejava
 * @since 2025-06-13 20:03:53
 */
@Data
public class AuthPermission implements Serializable {
    private static final long serialVersionUID = 202483765228754264L;
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
/**
     * 创建人
     */
    private String createdBy;
/**
     * 创建时间
     */
    private Date createdTime;
/**
     * 更新人
     */
    private String updateBy;
/**
     * 更新时间
     */
    private Date updateTime;
/**
     * 是否已删除 0否 1是
     */
    private Integer isDeleted;
}

