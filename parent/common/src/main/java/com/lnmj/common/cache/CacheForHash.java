package com.lnmj.common.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public final class CacheForHash extends RedisTemplateUtil {
    /**
     * 获取ListOperations
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static HashOperations opsForHash() {
        return redisTemplate.opsForHash();
    }

    /**
     * 新增hashMap值
     *
     * @param h
     * @param hk
     * @param hv
     * @param <H>
     * @param <HV>
     */
    @SuppressWarnings("unchecked")
    public static <H, HK, HV> void set(H h, HK hk, HV hv) {
        try {
            opsForHash().put(h, hk, hv);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 获取指定变量中的hashMap值
     *
     * @param hk
     * @param <HK>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <HK, HV> List<HV> values(HK hk) {
        try {
            return opsForHash().values(hk);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取指定变量中的hashMap值
     *
     * @param h
     */
    @SuppressWarnings("unchecked")
    public static <H, HK, HV> Map<HK, HV> entries(H h) {
        try {
            return opsForHash().entries(h);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取变量中的指定map键是否有值,如果存在该map键则获取值，没有则返回null
     *
     * @param h
     * @param hashKey
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <H> Object get(H h, Object hashKey) {
        try {
            return opsForHash().get(h, hashKey);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取变量中的指定map键是否有值,如果存在该map键则获取值，没有则返回null
     *
     * @param h
     * @param hashKey
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <H, T> T get(H h, Object hashKey, Class<T> classze) {
        try {
            return (T) opsForHash().get(h, hashKey);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 判断变量中是否有指定的map键
     *
     * @param h
     * @param hashKey
     */
    @SuppressWarnings("unchecked")
    public static <H> Boolean hasKey(H h, Object hashKey) {
        try {
            return opsForHash().hasKey(h, hashKey);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 获取变量中的键
     *
     * @param h
     */
    @SuppressWarnings("unchecked")
    public static <H, HK> Set<HK> keys(H h) {
        try {
            return opsForHash().keys(h);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取变量的长度
     *
     * @param h
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <H> Long size(H h) {
        try {
            return opsForHash().size(h);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 使变量中的键以double值的大小进行自增长
     *
     * @param h
     * @param hk
     * @param delta
     */
    @SuppressWarnings("unchecked")
    public static <H, HK> Double increment(H h, HK hk, double delta) {
        try {
            return opsForHash().increment(h, hk, delta);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 使变量中的键以long值的大小进行自增长
     *
     * @param h
     * @param hk
     * @param delta
     */
    @SuppressWarnings("unchecked")
    public static <H, HK> Long increment(H h, HK hk, long delta) {
        try {
            return opsForHash().increment(h, hk, delta);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 以集合的方式获取变量中的值
     *
     * @param h
     * @param hashKeys
     */
    @SuppressWarnings("unchecked")
    public static <H, HK, HV> List<HV> multiGet(H h, Collection<HK> hashKeys) {
        try {
            return opsForHash().multiGet(h, hashKeys);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 以map集合的形式添加键值对
     *
     * @param h
     * @param map
     */
    @SuppressWarnings("unchecked")
    public static <H, HK, HV> void putAll(H h, Map<? extends HK, ? extends HV> map) {
        try {
            opsForHash().putAll(h, map);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 以map集合的形式添加键值对
     *
     * @param h
     * @param hk
     * @param hv
     */
    @SuppressWarnings("unchecked")
    public static <H, HK, HV> Boolean putIfAbsent(H h, HK hk, HV hv) {
        try {
            return opsForHash().putIfAbsent(h, hk, hv);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 匹配获取键值对，ScanOptions.NONE为获取全部键对，ScanOptions.scanOptions().match("map1").build()     匹配获取键位map1的键值对,不能模糊匹配
     *
     * @param h
     * @param options
     */
    @SuppressWarnings("unchecked")
    public static <H, HK, HV> Cursor<Map.Entry<HK, HV>> scan(H h, ScanOptions options) {
        try {
            return opsForHash().scan(h, options);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 删除变量中的键值对，可以传入多个参数，删除多个键值对
     *
     * @param h
     * @param hashKeys
     */
    @SuppressWarnings("unchecked")
    public static <H> Long delete(H h, Object... hashKeys) {
        try {
            return opsForHash().delete(h, hashKeys);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
