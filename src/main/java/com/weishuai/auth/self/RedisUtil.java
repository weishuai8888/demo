package com.weishuai.auth.self;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisUtil {

    /**
     * 私有化构造器
     */
    private RedisUtil() {
    }

    private static final RedisTemplate<String, Object> REDIS_TEMPLATE = com.weishuai.auth.others.SpringContextUtils.getBean("redisTemplate", RedisTemplate.class);

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public static boolean expire(String key, long time) {
        try {
            if (time > 0) {
                REDIS_TEMPLATE.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("设置redis指定key失效时间错误:", e);
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效 失效时间为负数，说明该主键未设置失效时间（失效时间默认为-1）
     */
    public static Long getExpire(String key) {
        return REDIS_TEMPLATE.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false 不存在
     */
    public static Boolean hasKey(String key) {
        try {
            return REDIS_TEMPLATE.hasKey(key);
        } catch (Exception e) {
            log.error("redis判断key是否存在错误：", e);
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public static void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                REDIS_TEMPLATE.delete(key[0]);
            } else {
                REDIS_TEMPLATE.delete(Arrays.asList(key));
            }
        }
    }

    //============================map=============================
    public static <T> T get(String key, String hashKey) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(hashKey)) {
            return null;
        } else {
            return (T) REDIS_TEMPLATE.opsForHash().get(key, hashKey);
        }
    }

    public static <K, V> void put(String key, Map<? extends K, ? extends V> t) {
        REDIS_TEMPLATE.opsForHash().putAll(key, t);
    }
    //============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return key == null ? null : (T) REDIS_TEMPLATE.opsForValue().get(key);
    }

    public static <T> T getAndDelete(String key) {
        T t = get(key);
        del(key);
        return t;
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public static boolean set(String key, Object value) {
        try {
            REDIS_TEMPLATE.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("设置redis缓存错误：", e);
            return false;
        }

    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public static boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                REDIS_TEMPLATE.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public static boolean set(String key, Object value, long time, TimeUnit timeUnit) {
        try {
            if (time > 0) {
                REDIS_TEMPLATE.opsForValue().set(key, value, time, timeUnit);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
