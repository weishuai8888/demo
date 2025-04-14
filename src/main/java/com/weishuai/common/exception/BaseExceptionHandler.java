package com.weishuai.common.exception;

import com.weishuai.common.CommonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public CommonResult error(HttpServletRequest request, HttpServletResponse response, BizException e) {
        return CommonResult.fail(e);
    }
}
