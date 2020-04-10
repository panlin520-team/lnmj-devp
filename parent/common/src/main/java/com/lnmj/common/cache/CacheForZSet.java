package com.lnmj.common.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CacheForZSet extends RedisTemplateUtil {
    /**
     * 获取ZSetOperations 对象
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static ZSetOperations opsForZSet() {
        return redisTemplate.opsForZSet();
    }

    /**
     * 添加元素到变量中同时指定元素的分值
     *
     * @param k
     * @param v
     * @param score
     * @param <V>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Boolean add(K k, V v, double score) {
        try {
            return opsForZSet().add(k, v, score);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 添加元素到变量中同时指定元素的分值
     *
     * @param k
     * @param v
     * @param score
     * @param timeout  过期时间
     * @param timeUnit 过期时间单位
     * @param <V>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Boolean add(K k, V v, double score, long timeout, TimeUnit timeUnit) {
        try {
            Boolean result = opsForZSet().add(k, v, score);
            expire(k, timeout, timeUnit);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取变量指定区间的元素
     *
     * @param k
     * @param end
     * @param start
     * @param <V>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Set<V> range(K k, long start, long end) {
        try {
            return opsForZSet().range(k, start, end);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 用于获取满足非score的排序取值。这个排序只有在有相同分数的情况下才能使用，如果有不同的分数则返回值不确定
     *
     * @param k
     * @param range
     * @param <V>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Set<V> rangeByLex(K k, RedisZSetCommands.Range range) {
        try {
            return opsForZSet().rangeByLex(k, range);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 用于获取满足非score的排序取值。这个排序只有在有相同分数的情况下才能使用，如果有不同的分数则返回值不确定
     *
     * @param k
     * @param range
     * @param limit
     * @param <V>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Set<V> rangeByLex(K k, RedisZSetCommands.Range range, RedisZSetCommands.Limit limit) {
        try {
            return opsForZSet().rangeByLex(k, range, limit);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 通过TypedTuple方式新增数据
     *
     * @param k
     * @param tuples
     * @param <V>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Long add(K k, Set<ZSetOperations.TypedTuple<V>> tuples) {
        try {
            return opsForZSet().add(k, tuples);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 根据设置的score获取区间值
     *
     * @param k
     * @param min
     * @param max
     * @param <V>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Set<V> rangeByScore(K k, double min, double max) {
        try {
            return opsForZSet().rangeByScore(k, min, max);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据设置的score获取区间值从给定下标和给定长度获取最终值
     *
     * @param k
     * @param min
     * @param max
     * @param offset
     * @param count
     * @param <V>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Set<V> rangeByScore(K k, double min, double max, long offset, long count) {
        try {
            return opsForZSet().rangeByScore(k, min, max, offset, count);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取RedisZSetCommands.Tuples的区间值
     *
     * @param k
     * @param start
     * @param end
     * @param <V>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Set<V> rangeWithScores(K k, long start, long end) {
        try {
            return opsForZSet().rangeWithScores(k, start, end);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取RedisZSetCommands.Tuples的区间值通过分值
     *
     * @param k
     * @param min
     * @param max
     * @param <V>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Set<V> rangeByScoreWithScores(K k, double min, double max) {
        try {
            return opsForZSet().rangeByScoreWithScores(k, min, max);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取RedisZSetCommands.Tuples的区间值从给定下标和给定长度获取最终值通过分值
     *
     * @param k
     * @param min
     * @param max
     * @param offset
     * @param count
     * @param <V>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Set<V> rangeByScoreWithScores(K k, double min, double max, long offset, long count) {
        try {
            return opsForZSet().rangeByScoreWithScores(k, min, max, offset, count);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 获取RedisZSetCommands.Tuples的区间值从给定下标和给定长度获取最终值通过分值
     *
     * @param k
     * @param min
     * @param max
     * @param <K>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K> Long count(K k, double min, double max) {
        try {
            return opsForZSet().count(k, min, max);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取变量中元素的索引,下标开始位置为0
     *
     * @param k
     * @param o
     * @param <K>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K> Long rank(K k, Object o) {
        try {
            return opsForZSet().rank(k, o);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 匹配获取键值对，ScanOptions.NONE为获取全部键值对；ScanOptions.scanOptions().match("C").build()匹配获取键位map1的键值对,不能模糊匹配
     *
     * @param k
     * @param options
     * @param <K>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K> Long scan(K k, ScanOptions options) {
        try {
            return opsForZSet().rank(k, options);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取元素的分值
     *
     * @param k
     * @param o
     * @param <K>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K> Double score(K k, Object o) {
        try {
            return opsForZSet().score(k, o);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取变量中元素的个数。
     *
     * @param k
     * @param <K>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K> Long zCard(K k) {
        try {
            return opsForZSet().zCard(k);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 修改变量中的元素的分值。
     *
     * @param k
     * @param <K>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Double incrementScore(K k, V v, double delta) {
        try {
            return opsForZSet().incrementScore(k, v, delta);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 索引倒序排列指定区间元素
     *
     * @param k
     * @param <K>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Set<V> reverseRange(K k, long start, long end) {
        try {
            return opsForZSet().reverseRange(k, start, end);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 倒序排列指定分值区间元素
     *
     * @param k
     * @param <K>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Set<V> reverseRangeByScore(K k, double min, double max) {
        try {
            return opsForZSet().reverseRangeByScore(k, min, max);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 倒序排列从给定下标和给定长度分值区间元素
     *
     * @param k
     * @param <K>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Set<V> reverseRangeByScore(K k, double min, double max, long offset, long count) {
        try {
            return opsForZSet().reverseRangeByScore(k, min, max);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 倒序排序获取RedisZSetCommands.Tuples的分值区间值
     *
     * @param k
     * @param <K>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Set<V> reverseRangeByScoreWithScores(K k, double min, double max) {
        try {
            return opsForZSet().reverseRangeByScoreWithScores(k, min, max);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 倒序排序获取RedisZSetCommands.Tuples的从给定下标和给定长度分值区间值
     *
     * @param k
     * @param <K>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Set<V> reverseRangeByScoreWithScores(K k, double min, double max, long offset, long count) {
        try {
            return opsForZSet().reverseRangeByScoreWithScores(k, min, max, offset, count);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 索引倒序排列区间值
     *
     * @param k
     * @param <K>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Set<V> reverseRangeWithScores(K k, long start, long end) {
        try {
            return opsForZSet().reverseRangeByScoreWithScores(k, start, end);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取倒序排列的索引值
     *
     * @param k
     * @param o
     * @param <K>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K> Long reverseRank(K k, Object o) {
        try {
            return opsForZSet().reverseRank(k, o);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取2个变量的交集存放到第3个变量里面
     *
     * @param k
     * @param k1
     * @param k2
     * @param <K>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, K1, K2> Long intersectAndStore(K k, K1 k1, K2 k2) {
        try {
            return opsForZSet().intersectAndStore(k, k1, k2);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取多个变量的交集存放到第3个变量里面
     *
     * @param k
     * @param k1
     * @param k2
     * @param <K>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, K1, K2> Long intersectAndStore(K k, Collection<K1> k1, K2 k2) {
        try {
            return opsForZSet().intersectAndStore(k, k1, k2);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取2个变量的合集存放到第3个变量里面。
     *
     * @param k
     * @param k1
     * @param k2
     * @param <K>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, K1, K2> Long unionAndStore(K k, K1 k1, K2 k2) {
        try {
            return opsForZSet().unionAndStore(k, k1, k2);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取多个变量的合集存放到第3个变量里面
     *
     * @param k
     * @param k1
     * @param k2
     * @param <K>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, K1, K2> Long unionAndStore(K k, Collection<K1> k1, K2 k2) {
        try {
            return opsForZSet().unionAndStore(k, k1, k2);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 批量移除元素根据元素值。
     *
     * @param k
     * @param values
     * @param <K>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K> Long remove(K k, Object... values) {
        try {
            return opsForZSet().remove(k, values);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据分值移除区间元素。
     *
     * @param k
     * @param min
     * @param max
     * @param <K>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K> Long removeRangeByScore(K k, double min, double max) {
        try {
            return opsForZSet().removeRangeByScore(k, min, max);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据分值移除区间元素。
     *
     * @param k
     * @param start
     * @param end
     * @param <K>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K> Long removeRange(K k, long start, long end) {
        try {
            return opsForZSet().removeRange(k, start, end);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
