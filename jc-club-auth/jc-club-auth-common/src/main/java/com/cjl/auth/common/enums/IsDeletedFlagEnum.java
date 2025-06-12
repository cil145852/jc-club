package com.cjl.auth.common.enums;

import lombok.Getter;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-03-14:36
 * @Description 删除状态枚举
 */
@Getter
public enum IsDeletedFlagEnum {

    DELETED(1, "已删除"),
    UN_DELETED(0, "未删除");

    public Integer code;
    public String desc;

    IsDeletedFlagEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static IsDeletedFlagEnum getByCode(Integer code) {
        for (IsDeletedFlagEnum resultCodeEnum : IsDeletedFlagEnum.values()) {
            if (resultCodeEnum.code.equals(code)) {
                return resultCodeEnum;
            }
        }
        return null;
    }
}
