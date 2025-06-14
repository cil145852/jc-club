package com.cjl.auth.infra.basic.service;

import com.cjl.auth.infra.basic.entity.AuthPermission;

import java.util.List;

/**
 * 权限信息表(AuthPermission)表服务接口
 *
 * @author makejava
 * @since 2025-06-13 20:03:53
 */
public interface AuthPermissionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AuthPermission queryById(Long id);

    /**
     * 新增数据
     *
     * @param authPermission 实例对象
     */
    Integer insert(AuthPermission authPermission);

    /**
     * 修改数据
     *
     * @param authPermission 实例对象
     * @return
     */
    Integer update(AuthPermission authPermission);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 通过多个id查询多条数据
     */
    List<AuthPermission> queryByIds(List<Long> permissionIds);
}
