package com.weishuai.auth.self;

import cn.hutool.jwt.RegisteredPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weishuai.auth.others.JWTUtils;
import com.weishuai.auth.others.RSAUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description TODO
 * @Author ws
 * @Date 2025/4/12 10:48
 */
@Slf4j
@Aspect
@Component
public class HeaderAuthAspect {

    @Value("${thirdParty.xAccessTokenPrivateKey}")
    private String xAccessTokenPrivateKey;
    @Value("${thirdParty.xAccessTokenSecret}")
    private String xAccessTokenSecret;


    /**
     * 核心拦截逻辑
     */
    @Around("@annotation(auth)")
    public Object checkHeaderAuth(ProceedingJoinPoint pjp, HeaderAuth auth) throws Throwable {
        // 1. 获取当前HTTP请求
        HttpServletRequest request = getCurrentRequest();

        // 2. 获取指定Header
        String actualTokenValue = request.getHeader(auth.headerTokenKey());
        int timeoutValue = request.getIntHeader(auth.headerTimeoutKey());
        log.warn("HeaderAuthAspect: actualTokenValue = {}, timeoutValue = {}", actualTokenValue, timeoutValue);

        // 3. 校验逻辑
        validateHeader(auth, actualTokenValue, timeoutValue);

        // 4. 执行原方法
        return pjp.proceed();
    }

    private HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalStateException("当前非HTTP请求上下文环境");
        }
        return attributes.getRequest();
    }

    private void validateHeader(HeaderAuth auth, String actualTokenValue, int timeoutValue) throws JsonProcessingException {
        // Case 1: 必需但不存在
        if (auth.requiredToken() && StringUtils.isBlank(actualTokenValue)) {
            throw createException(auth, "Header验证失败: header中的密文token为空");
        }
        // token配置的过期时间是否在允许的范围内
        if (timeoutValue <= 0 || timeoutValue > 60 * 60) {
            throw createException(auth, "Header验证失败: 超时时间不符合预期值");
        }
        matchValue(auth, actualTokenValue, timeoutValue);
    }

    private void matchValue(HeaderAuth auth, String actualTokenValue, int timeoutValue) throws JsonProcessingException {
        String decryptContent = RSAUtils.decrypt(actualTokenValue, xAccessTokenPrivateKey);
        log.warn("HeaderAuthAspect: decryptContent = {}", decryptContent);
        if (StringUtils.isBlank(decryptContent)) {
            throw createException(auth, "Header验证失败: token明文为空");
        }

        int iat = JWTUtils.getPayload(decryptContent, RegisteredPayload.ISSUED_AT);
        String timestamp = iat == 0 ? "" : String.valueOf(iat);
        String tokenValue = RedisUtil.get(timestamp);
        if (StringUtils.isNotBlank(tokenValue)) {
            throw createException(auth, "Header验证失败: token已使用");
        }
        RedisUtil.set(timestamp, actualTokenValue, timeoutValue);

        if (!JWTUtils.validate(decryptContent, xAccessTokenSecret)) {
            throw createException(auth, "Header验证失败: token无效");
        }
    }

    private RuntimeException createException(HeaderAuth auth, String detail) {
        try {
            return auth.RuntimeException()
                    .getDeclaredConstructor(String.class)
                    .newInstance(auth.message() + " - " + detail);
        } catch (Exception e) {
            return new ForbiddenException(detail);
        }
    }

    private static Map<String, Object> stringToMap(String str) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        // 安全反序列化（防JSON注入）
        return mapper.readValue(str,
                new TypeReference<Map<String, Object>>() {
                });
    }
}
