package com.lnmj.common.cache;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisTemplateUtil {
    // redis模板
    protected static RedisTemplate redisTemplate = SpringContextHolder.getBean("redisTemplate");

    //更新RedisTemplate
    public static void setRedisTemplate() {
        redisTemplate = SpringContextHolder.getBean("redisTemplate");
    }

    /**
     * 设置过期时间
     *
     * @param k
     * @param timeout
     */
    @SuppressWarnings("unchecked")
    public static <K> void expire(K k, long timeout, TimeUnit timeUnit) {
        try {
            redisTemplate.expire(k, timeout, timeUnit);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 获取过期时间
     *
     * @param k
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <K> long getExpire(K k) {
        try {
            return redisTemplate.getExpire(k);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return -1;
    }

    /**
     * 获取过期时间
     *
     * @param k
     * @param timeUnit 单位
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <K> long getExpire(K k, TimeUnit timeUnit) {
        try {
            return redisTemplate.getExpire(k, timeUnit);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return -1;
    }

    /**
     * 模糊搜索
     *
     * @param pattern
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Set<T> Keys(T pattern) {
        try {
            return redisTemplate.keys(pattern);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 删除值
     *
     * @param k
     */
    public static <K> void delete(K k) {
        expire(k, 1, TimeUnit.MILLISECONDS);
    }
}
