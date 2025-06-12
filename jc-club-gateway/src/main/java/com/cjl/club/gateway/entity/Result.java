package com.cjl.club.gateway.entity;

import com.cjl.club.gateway.enums.ResultCodeEnum;
import lombok.Data;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-02-20:50
 * @Description
 */
@Data
public class Result<T> {
    private Integer code;
    private Boolean success;
    private String message;
    private T data;

    public static Result ok() {
        Result result = new Result<>();
        ResultCodeEnum successCode = ResultCodeEnum.SUCCESS;
        result.setCode(successCode.getCode());
        result.setSuccess(true);
        result.setMessage(successCode.getDesc());
        return result;
    }
    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        ResultCodeEnum  successCode = ResultCodeEnum.SUCCESS;
        result.setCode(successCode.getCode());
        result.setMessage(successCode.getDesc());
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static Result fail() {
        Result result = new Result<>();
        ResultCodeEnum  failCode = ResultCodeEnum.FAIL;
        result.setCode(failCode.getCode());
        result.setSuccess(false);
        result.setMessage(failCode.getDesc());
        return result;
    }
    public static <T> Result<T> fail(T data) {
        Result<T> result = new Result<>();
        ResultCodeEnum  failCode = ResultCodeEnum.SUCCESS;
        result.setCode(failCode.getCode());
        result.setMessage(failCode.getDesc());
        result.setSuccess(false);
        result.setData(data);
        return result;
    }
    public static Result fail(Integer code, String message) {
        Result result = new Result<>();
        result.setCode(code);
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }
}
