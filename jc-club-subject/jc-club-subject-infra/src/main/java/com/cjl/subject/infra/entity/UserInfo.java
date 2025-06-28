package com.cjl.subject.infra.entity;

import lombok.Data;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-28-15:27
 * @Description
 */

@Data
public class UserInfo {

    /**
     * 账号
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;
}