package com.weishuai.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.weishuai.common.exception.AbstractBaseExceptionEnum;
import com.weishuai.common.exception.BizException;
import lombok.Getter;
import lombok.Setter;

/**
 * 统一返回类
 *
 * @author ws
 * @date 2020/4/7下午12:14
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResult<T> {

    /**
     * 状态码
     */
    private String code;
    /**
     * 原因
     */
    private String msg;

    private T data;

    public CommonResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private CommonResult(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> CommonResult<T> success() {
        return new CommonResult<>(ResultCode.R_2000_SUCCESS.getCode(), ResultCode.R_2000_SUCCESS.getMsg());
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ResultCode.R_2000_SUCCESS.getCode(), ResultCode.R_2000_SUCCESS.getMsg(), data);
    }

    public static <T> CommonResult<T> result(ResultCode resultCode, T data) {
        return new CommonResult<T>(resultCode.getCode(), resultCode.getMsg(), data);
    }

    public static <T> CommonResult<T> result(String code, String msg, T data) {
        return new CommonResult<T>(code, msg, data);
    }

    public static <T> CommonResult<T> fail() {
        return new CommonResult<T>(ResultCode.R_5000_SERVER_ERROR.getCode(), ResultCode.R_5000_SERVER_ERROR.getMsg());
    }

    public static <T> CommonResult<T> fail(ResultCode resultCode) {
        return new CommonResult<>(resultCode.getCode(), resultCode.getMsg());
    }

    public static <T> CommonResult<T> fail(BizException e) {
        return new CommonResult<>(ResultCode.R_5000_SERVER_ERROR.getCode(), e.getMessage());
    }

    public static <T> CommonResult<T> fail(AbstractBaseExceptionEnum exceptionEnum) {
        return new CommonResult<T>(exceptionEnum.getCode(), exceptionEnum.getReason() + exceptionEnum.getThrowable().getMessage());
    }

}
