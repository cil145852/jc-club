package com.cjl.auth.infra.basic.service.impl;

import com.cjl.auth.infra.basic.entity.AuthUserRole;
import com.cjl.auth.infra.basic.mapper.AuthUserRoleDao;
import com.cjl.auth.infra.basic.service.AuthUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户角色关联表(AuthUserRole)表服务实现类
 *
 * @author makejava
 * @since 2025-06-13 14:45:56
 */
@Service("authUserRoleService")
public class AuthUserRoleServiceImpl implements AuthUserRoleService {
    @Resource
    private AuthUserRoleDao authUserRoleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public AuthUserRole queryById(Long id) {
        return this.authUserRoleDao.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param authUserRole 实例对象
     * @return 实例对象
     */
    @Override
    public Integer insert(AuthUserRole authUserRole) {
        return this.authUserRoleDao.insert(authUserRole);
    }

    /**
     * 修改数据
     *
     * @param authUserRole 实例对象
     * @return 实例对象
     */
    @Override
    public Integer update(AuthUserRole authUserRole) {
        return this.authUserRoleDao.update(authUserRole);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.authUserRoleDao.deleteById(id) > 0;
    }
}
