package com.cjl.auth.entity;

import lombok.Getter;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-02-20:54
 * @Description
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(200, "成功"),
    FAIL(500, "失败");

    public Integer code;
    public String desc;

    ResultCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ResultCodeEnum getByCode(Integer code) {
        for (ResultCodeEnum resultCodeEnum : ResultCodeEnum.values()) {
            if (resultCodeEnum.code.equals(code)) {
                return resultCodeEnum;
            }
        }
        return null;
    }
}
