package com.weishuai.common.exception;


/**
 * @Description : 业务异常类
 * @Author : Future Buddha
 * @Date: 2021-12-02 11:19
 */
public class SystemException extends RuntimeException {

    public SystemException(AbstractBaseExceptionEnum exception) {
        super(exception.getReason());
    }

    public SystemException(SystemExceptionEnum exception) {
        super(exception.getReason(), exception.getThrowable());
    }

}
