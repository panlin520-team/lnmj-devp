package com.lnmj.common.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * String 存储方式
 */
@Slf4j
public final class CacheForValue extends RedisTemplateUtil {
    /**
     * 获取ValueOperations对象
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static ValueOperations opsForValue() {
        return redisTemplate.opsForValue();
    }

    /**
     * 添加
     *
     * @param k
     * @param v
     */
    @SuppressWarnings("unchecked")
    public static <K, V> void add(K k, V v) {
        try {
            opsForValue().set(k, v);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 添加并设置过期时间
     *
     * @param k
     * @param v
     * @param seconds 秒
     */
    @SuppressWarnings("unchecked")
    public static <K, V> void add(K k, V v, int seconds) {
        try {
            opsForValue().set(k, v, seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 设置过期时间
     *
     * @param k
     * @param v
     * @param milliseconds 毫秒
     */
    @SuppressWarnings("unchecked")
    public static <K, V> void add(K k, V v, long milliseconds) {
        try {
            opsForValue().set(k, v, milliseconds, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 设置过期时间
     *
     * @param k
     * @param v
     * @param timeout  时间
     * @param timeUnit 时间单位 例如 TimeUnit.HOURS TimeUnit.MILLISECONDS TimeUnit.SECONDS
     */
    @SuppressWarnings("unchecked")
    public static <K, V> void add(K k, V v, long timeout, TimeUnit timeUnit) {
        try {
            opsForValue().set(k, v, timeout, timeUnit);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 如果键不存在则新增 ，有则不改变
     *
     * @param k
     * @param v
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Boolean setIfAbsent(K k, V v) {
        try {
            return opsForValue().setIfAbsent(k, v);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 以增量的方式将long值存储在变量中
     *
     * @param k
     * @param delta
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K> Long increment(K k, long delta) {
        try {
            return opsForValue().increment(k, delta);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 以增量的方式将double值存储在变量中
     *
     * @param k
     * @param delta
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K> Double increment(K k, double delta) {
        try {
            return opsForValue().increment(k, delta);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 在原有的值基础上新增字符串到末尾
     *
     * @param k
     * @param v
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K> Object append(K k, String v) {
        try {
            return opsForValue().getAndSet(k, v);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取原来key键对应的值并重新赋新值并设置过期时间
     *
     * @param k
     * @param v
     * @param timeout  过期时间
     * @param timeUnit 设置时间单位
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Object getAndSet(K k, V v, long timeout, TimeUnit timeUnit) {
        try {
            Object o = opsForValue().getAndSet(k, v);
            expire(k, timeout, timeUnit);
            return o;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取原来key键对应的值并重新赋新值
     *
     * @param k
     * @param v
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K> Object getAndSet(K k, String v) {
        try {
            return opsForValue().getAndSet(k, v);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 获取
     *
     * @param k
     * @return
     */
    @Nullable
    public static <K> Object get(K k) {
        try {
            return opsForValue().get(k);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取
     *
     * @param k
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> V get(K k, Class<V> classze) {
        try {
            return (V) opsForValue().get(k);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 截取key键对应值得字符串，从开始下标位置开始到结束下标的位置(包含结束下标)的字符串
     *
     * @param k
     * @param start 开始位置
     * @param end   结束位置
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K> String get(K k, long start, long end) {
        try {
            return opsForValue().get(k, start, end);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 设置map集合到redis
     *
     * @param map
     * @param <K>
     * @param <V>
     */
    @SuppressWarnings("unchecked")
    public static <K, V> void multiSet(Map<K, V> map) {
        try {
            opsForValue().multiSet(map);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 如果对应的map集合名称不存在，则添加，如果存在则不做修改
     *
     * @param map
     * @param <K>
     * @param <V>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Boolean multiSetIfAbsent(Map<K, V> map) {
        try {
            return opsForValue().multiSetIfAbsent(map);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据集合取出对应的value值。
     *
     * @param collection
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> List<V> multiGet(Collection<K> collection) {
        try {
            return opsForValue().multiGet(collection);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * key键对应的值value对应的ascii码,在offset的位置(从左向右数)变为value
     *
     * @param k
     * @param offset
     * @param value
     * @param <K>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K> Boolean setBit(K k, long offset, boolean value) {
        try {
            return opsForValue().setBit(k, offset, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 判断指定的位置ASCII码的bit位是否为1
     *
     * @param k
     * @param offset
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K> Boolean getBit(K k, long offset) {
        try {
            return opsForValue().getBit(k, offset);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
