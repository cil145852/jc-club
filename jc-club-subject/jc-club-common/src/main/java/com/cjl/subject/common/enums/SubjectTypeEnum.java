package com.cjl.subject.common.enums;

import lombok.Getter;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-04-9:21
 * @Description 删除状态枚举
 */
@Getter
public enum SubjectTypeEnum {

    RADIO(1, "单选"),
    MULTIPLE(2, "多选"),
    JUDGE(3, "判断"),
    BRIEF(4, "简答");

    public Integer code;
    public String desc;

    SubjectTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SubjectTypeEnum getByCode(Integer code) {
        for (SubjectTypeEnum resultCodeEnum : SubjectTypeEnum.values()) {
            if (resultCodeEnum.code.equals(code)) {
                return resultCodeEnum;
            }
        }
        return null;
    }
}
