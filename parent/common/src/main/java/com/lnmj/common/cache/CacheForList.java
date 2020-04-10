package com.lnmj.common.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public final class CacheForList extends RedisTemplateUtil {
    /**
     * 获取ListOperations
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static ListOperations opsForList() {
        return redisTemplate.opsForList();
    }

    /**
     * 在变量左边添加元素值
     *
     * @param k   键
     * @param v   值
     * @param <K>
     * @param <V>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Long leftPush(K k, V v) {
        try {
            return opsForList().leftPush(k, v);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 插入到指定的位置
     *
     * @param k
     * @param index
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K> Object index(K k, long index) {
        try {
            return opsForList().index(k, index);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 获取指定区间的值
     *
     * @param k
     * @param start
     * @param end
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> List<V> range(K k, long start, long end) {
        try {
            return opsForList().range(k, start, end);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 把最后一个参数值放到指定集合的第一个出现中间参数的前面，如果中间参数值存在的话
     *
     * @param k
     * @param v
     * @param v1
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V, V1> Long leftPush(K k, V v, V1 v1) {
        try {
            return opsForList().leftPush(k, v, v1);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 向左边批量添加参数元素
     *
     * @param k
     * @param values
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Long leftPushAll(K k, V... values) {
        try {
            return opsForList().leftPushAll(k, values);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 向左边批量添加参数元素
     *
     * @param k
     * @param values
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Long leftPushAll(K k, Collection<V> values) {
        try {
            return opsForList().leftPushAll(k, values);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 如果存在集合则添加元素
     *
     * @param k
     * @param v
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Long leftPushIfPresent(K k, V v) {
        try {
            return opsForList().leftPushIfPresent(k, v);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 向集合最右边添加元素
     *
     * @param k
     * @param v
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Long rightPush(K k, V v) {
        try {
            return opsForList().rightPush(k, v);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 向集合中第一次出现第二个参数变量元素的右边添加第三个参数变量的元素值
     *
     * @param k
     * @param v
     * @param v1
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V, V1> Long rightPush(K k, V v, V1 v1) {
        try {
            return opsForList().rightPush(k, v, v1);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 向右边批量添加元素
     *
     * @param k
     * @param v
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Long rightPushAll(K k, V... v) {
        try {
            return opsForList().rightPushAll(k, v);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 以集合方式向右边添加元素
     *
     * @param k
     * @param v
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Long rightPushAll(K k, Collection<V>... v) {
        try {
            return opsForList().rightPushAll(k, v);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 向已存在的集合中添加元素
     *
     * @param k
     * @param v
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Long rightPushIfPresent(K k, V... v) {
        try {
            return opsForList().rightPushIfPresent(k, v);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取集合长度
     *
     * @param k
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K> Long size(K k) {
        try {
            return opsForList().size(k);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 移除集合中的左边第一个元素
     *
     * @param k
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K> Object leftPop(K k) {
        try {
            return opsForList().leftPop(k);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 移除集合中左边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出
     *
     * @param k
     * @param timeout  超时
     * @param timeUnit 超时单位
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K> Object leftPop(K k, long timeout, TimeUnit timeUnit) {
        try {
            return opsForList().leftPop(k, timeout, timeUnit);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 移除集合中右边的元素
     *
     * @param k
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K> Object rightPop(K k) {
        try {
            return opsForList().leftPop(k);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 移除集合中右边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出
     *
     * @param k
     * @param timeout  超时
     * @param timeUnit 超时单位
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K> Object rightPop(K k, long timeout, TimeUnit timeUnit) {
        try {
            return opsForList().leftPop(k, timeout, timeUnit);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 移除集合中右边的元素，同时在左边加入一个元素
     *
     * @param k
     * @param k1
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, K1> Object rightPopAndLeftPush(K k, K1 k1) {
        try {
            return opsForList().rightPopAndLeftPush(k, k1);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 移除集合中右边的元素在等待的时间里，同时在左边添加元素，如果超过等待的时间仍没有元素则退出。
     *
     * @param k
     * @param k1
     * @param <K>
     * @param timeUnit 时间单位
     * @param timeout  超时时间
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, K1> Object rightPopAndLeftPush(K k, K1 k1, long timeout, TimeUnit timeUnit) {
        try {
            return opsForList().rightPopAndLeftPush(k, k1, timeout, timeUnit);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 在集合的指定位置插入元素,如果指定位置已有元素，则覆盖，没有则新增，超过集合下标+n则会报错
     *
     * @param k
     * @param index
     * @param v
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <K, V> void set(K k, long index, V v) {
        try {
            opsForList().set(k, index, v);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 从存储在键中的列表中删除等于值的元素的第一个计数事件。count> 0：删除等于从左到右移动的值的第一个元素；count< 0：删除等于从右到左移动的值的第一个元素；count = 0：删除等于value的所有元素
     *
     * @param k
     * @param count
     * @param value
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K> Long remove(K k, long count, Object value) {
        try {
            return opsForList().remove(k, count, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 截取集合元素长度，保留长度内的数据
     *
     * @param k
     * @param start
     * @param end
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <K> void trim(K k, long start, long end) {
        try {
            opsForList().trim(k, start, end);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
