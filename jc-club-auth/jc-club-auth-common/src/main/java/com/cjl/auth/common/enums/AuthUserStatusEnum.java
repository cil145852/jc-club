package com.cjl.auth.common.enums;

import lombok.Getter;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-012-14:36
 * @Description 用户状态枚举
 */
@Getter
public enum AuthUserStatusEnum {

    OPEN(0, "启用"),
    CLOSE(1, "禁用");

    public Integer code;
    public String desc;

    AuthUserStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static AuthUserStatusEnum getByCode(Integer code) {
        for (AuthUserStatusEnum resultCodeEnum : AuthUserStatusEnum.values()) {
            if (resultCodeEnum.code.equals(code)) {
                return resultCodeEnum;
            }
        }
        return null;
    }
}
