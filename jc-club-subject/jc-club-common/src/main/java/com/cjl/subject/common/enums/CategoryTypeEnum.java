package com.cjl.subject.common.enums;

import lombok.Getter;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-03-14:36
 * @Description 删除状态枚举
 */
@Getter
public enum CategoryTypeEnum {

    PRIMARY(1, "岗位大类"),
    SECOND(2, "二级分类");

    public Integer code;
    public String desc;

    CategoryTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static CategoryTypeEnum getByCode(Integer code) {
        for (CategoryTypeEnum resultCodeEnum : CategoryTypeEnum.values()) {
            if (resultCodeEnum.code.equals(code)) {
                return resultCodeEnum;
            }
        }
        return null;
    }
}
