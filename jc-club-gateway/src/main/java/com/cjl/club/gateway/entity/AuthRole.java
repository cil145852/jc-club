package com.cjl.club.gateway.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色信息表(AuthRole)实体类
 *
 * @author makejava
 * @since 2025-06-13 11:22:40
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRole implements Serializable {
    private static final long serialVersionUID = -95531285079521546L;
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

