package com.weishuai.auth.self;

/**
 * @Description 权限校验专用异常（HTTP 403）
 * @Author ws
 * @Date 2025/4/12 10:48
 */
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
