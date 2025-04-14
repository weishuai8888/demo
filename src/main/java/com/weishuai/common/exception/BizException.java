package com.weishuai.common.exception;


/**
 * @Description : 业务异常类
 * @Author : Future Buddha
 * @Date: 2021-12-02 11:19
 */
public class BizException extends RuntimeException {

    public BizException(AbstractBaseExceptionEnum exception) {
        super(exception.getReason());
    }

    public BizException(UserExceptionEnum exception) {
        super(exception.getReason(), exception.getThrowable());
    }
}
