package com.cjl.auth.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-12-20:51
 * @Description
 */
@Data
public class AuthUserDTO implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 性别 1男 2女
     */
    private Integer sex;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 状态 0启用 1禁用
     */
    private Integer status;
    /**
     * 个人简介
     */
    private String introduce;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 用于扩展的特殊字段
     */
    private String extJson;
}
