package com.cjl.auth.infra.basic.service;

import com.cjl.auth.infra.basic.entity.AuthRolePermission;

import java.util.List;

/**
 * 角色权限关联表(AuthRolePermission)表服务接口
 *
 * @author makejava
 * @since 2025-06-13 21:00:50
 */
public interface AuthRolePermissionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AuthRolePermission queryById(Long id);

    /**
     * 新增数据
     *
     * @param authRolePermission 实例对象
     * @return
     */
    Integer insert(AuthRolePermission authRolePermission);

    /**
     * 修改数据
     *
     * @param authRolePermission 实例对象
     * @return
     */
    Integer update(AuthRolePermission authRolePermission);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    Integer batchInsert(List<AuthRolePermission> authRolePermissionList);
}
