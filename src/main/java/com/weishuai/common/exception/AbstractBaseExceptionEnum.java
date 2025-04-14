package com.weishuai.common.exception;

/**
 * @Description : 异常信息接口
 * @Author : Future Buddha
 * @Date: 2021-12-02 11:22
 */
public interface AbstractBaseExceptionEnum {
    /**
     * 获取异常的状态码
     * @return
     */
    String getCode();

    /**
     * 获取异常状态码的提示信息
     * @return
     */
    String getReason();

    /**
     * 补充说明
     * @param
     * @return
     */
    String getSupplementDesc();

    /**
     * 获取Throwable
     * @return
     */
    @Deprecated
    Throwable getThrowable();

    /**
     * 装配Throwable
     * @param val
     * @return
     */
    @Deprecated
    AbstractBaseExceptionEnum assembleThrowable(String val);

}
