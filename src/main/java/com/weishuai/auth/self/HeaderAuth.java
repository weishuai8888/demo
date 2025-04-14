package com.weishuai.auth.self;


import java.lang.annotation.*;

/**
 * @Description 基于请求头的权限校验注解
 * @Author ws
 * @Date 2025/4/12 10:46
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HeaderAuth {
    String headerTokenKey() default "X-Access-Token"; // 必须指定的header键
    boolean requiredToken() default true; // 是否必须存在

    String headerTimeoutKey() default "timeout"; // 必须指定的header键

    String expectedValue() default "";      // 预期值（支持简单模式匹配如*号通配符）

    Class<? extends RuntimeException> RuntimeException()
            default ForbiddenException.class;  // 校验失败时抛出的异常类型

    String message() default "Header验证失败"; // 异常消息模板
}