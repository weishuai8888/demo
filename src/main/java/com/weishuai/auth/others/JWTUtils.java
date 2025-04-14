package com.weishuai.auth.others;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.crypto.GlobalBouncyCastleProvider;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.RegisteredPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class JWTUtils {

    private JWTUtils() {
    }

    public static final String LOGIN_TYPE_SINGLE = "singleLogin";
    public static final String TOKEN = "token";
    public static final String SYSTEMINFO = "systeminfo";

    /**
     * 获取jwt携带的信息
     *
     * @param token
     * @param name
     * @param <T>
     * @return
     */
    public static <T> T getPayload(String token, String name) {
        try {
            JWT jwt = JWTUtil.parseToken(token);
            Object payload = jwt.getPayload(name);
            if (payload == null) {
                return null;
            } else {
                return (T) payload;
            }
        } catch (Exception e) {
            //jwt解析失败，不做处理
            return null;
        }
    }

    /**
     * 生成token
     *
     * @param payload 可以存放用户的一些信息，不要存放敏感字段
     * @param timeout jwt超时时间
     * @param key     HS256(HmacSHA256)密钥
     * @return
     */
    public static String createToken(Map<String, Object> payload, int timeout, String key) {
        //十分重要，不禁用发布到生产环境无法验证
        GlobalBouncyCastleProvider.setUseBouncyCastle(false);
        DateTime now = DateTime.now();
        DateTime expTime = now.offsetNew(DateField.SECOND, timeout);
        // 签发时间
        payload.put(RegisteredPayload.ISSUED_AT, now);
        // 过期时间
        payload.put(RegisteredPayload.EXPIRES_AT, expTime);
        // 生效时间
        payload.put(RegisteredPayload.NOT_BEFORE, now);
        String token = JWTUtil.createToken(payload, key.getBytes());
        log.info("生成JWT token：{}", token);
        return token;
    }

    /**
     * 检验token是否有效
     *
     * @param token jwt
     * @param key   HS256(HmacSHA256)密钥
     * @return
     */
    public static boolean validate(String token, String key) {
        GlobalBouncyCastleProvider.setUseBouncyCastle(false);
        try {
            JWT jwt = JWTUtil.parseToken(token).setKey(key.getBytes());
            // validate包含了verify
            boolean validate = jwt.validate(10); // 允许10秒钟时钟偏移
            log.info("JWT token校验结果：{}", validate);
            return validate;
        } catch (Exception e) {
            log.info("检验token异常{}", e.getMessage());
            return false;
        }
    }
}
