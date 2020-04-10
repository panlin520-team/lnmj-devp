package com.lnmj.common.cache;

import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.Object;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
public final class CacheForSet extends RedisTemplateUtil {
    /**
     * 反回SetOperations对象
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static SetOperations opsForSet() {
        return redisTemplate.opsForSet();
    }

    /**
     * 以set形式添加值
     *
     * @param k      键
     * @param values 值
     * @param <K>
     * @param <V>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Long addSet(K k, V... values) {
        try {
            return opsForSet().add(k, values);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 以set形式添加值并
     *
     * @param k        键
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     * @param values   值
     * @param <K>
     * @param <V>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Long addSet(K k, long timeout, TimeUnit timeUnit, V... values) {
        try {
            return opsForSet().add(k, values);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取变量中的值
     *
     * @param k
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Set<V> members(K k) {
        try {
            return opsForSet().members(k);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 随机获取变量中的元素
     *
     * @param k
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <K> Object randomMember(K k) {
        try {
            return (Object) opsForSet().randomMember(k);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 随机获取变量中指定个数的元素
     *
     * @param k
     * @param count
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <K, V> List<V> randomMembers(K k, long count) {
        try {
            return opsForSet().randomMembers(k, count);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 获取变量中值的长度。
     *
     * @param k
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K> Long size(K k) {
        try {
            return opsForSet().size(k);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 检查给定的元素是否在变量中。
     *
     * @param k
     * @param v
     * @param <K>
     * @param <V>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Boolean isMember(K k, V v) {
        try {
            return opsForSet().isMember(k, v);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 转移变量的元素值到目的变量。
     *
     * @param k
     * @param destKey
     * @param v
     * @param <K>
     * @param <K1>
     * @param <V>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, K1, V> Boolean move(K k, K1 destKey, V v) {
        try {
            return opsForSet().move(k, v, destKey);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 弹出变量中的元素
     *
     * @param k
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K> Object pop(K k) {
        try {
            return (Object) opsForSet().pop(k);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 批量移除变量中的元素。
     *
     * @param k
     * @param values
     * @param <K>
     * @param <V>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Long remove(K k, V... values) {
        try {
            return opsForSet().remove(k, values);
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
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Cursor<V> scan(K k, ScanOptions options) {
        try {
            return opsForSet().scan(k, options);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取集合之间不一样的值
     *
     * @param k
     * @param otherKeys
     * @param <K>
     * @param <V>
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Set<V> difference(K k, Collection<K> otherKeys) {
        try {
            return opsForSet().difference(k, otherKeys);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 获取两个集合之间不一样的值
     *
     * @param k
     * @param k1
     * @param <K>
     * @param <K1>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, K1, V> Set<V> difference(K k, K1 k1) {
        try {
            return opsForSet().difference(k, k1);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取三个集合之间不一样的值进行保存
     *
     * @param k
     * @param k1
     * @param k2
     * @param <K>
     * @param <K1>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <K, K1, K2> void differenceAndStore(K k, K1 k1, K2 k2) {
        try {
            opsForSet().differenceAndStore(k, k1, k2);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 获取集合之间不一样的值进行保存
     *
     * @param k
     * @param kls
     * @param k2
     * @param <K>
     * @param <K1>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <K, K1, K2> void differenceAndStore(K k, Collection<K1> kls, K2 k2) {
        try {
            opsForSet().differenceAndStore(k, kls, k2);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 获取去重的随机元素
     *
     * @param k
     * @param count
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, V> Set<V> distinctRandomMembers(K k, long count) {
        try {
            return opsForSet().distinctRandomMembers(k, count);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取2个变量中的交集
     *
     * @param k
     * @param k1
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, K1, V> Set<V> intersect(K k, K1 k1) {
        try {
            return opsForSet().intersect(k, k1);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取多个变量之间的交集
     *
     * @param k
     * @param k1
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, K1, V> Set<V> intersect(K k, Collection<K1> k1) {
        try {
            return opsForSet().intersect(k, k1);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取2个变量交集后保存到最后一个参数上
     *
     * @param k
     * @param k1
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <K, K1, K2> void intersectAndStore(K k, K1 k1, K2 k2) {
        try {
            opsForSet().intersectAndStore(k, k1, k2);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 获取多个变量的交集并保存到最后一个参数上
     *
     * @param k
     * @param k1
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <K, K1, K2> void intersectAndStore(K k, Collection<K1> k1, K2 k2) {
        try {
            opsForSet().intersectAndStore(k, k1, k2);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 获取多个变量的合集。
     *
     * @param k
     * @param k1
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, K1, V> Set<V> union(K k, Collection<K1> k1) {
        try {
            return opsForSet().union(k, k1);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取2个变量的合集
     *
     * @param k
     * @param k1
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, K1, V> Set<V> union(K k, K1 k1) {
        try {
            return opsForSet().union(k, k1);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 获取2个变量合集后保存到最后一个参数上
     *
     * @param k
     * @param k1
     * @param k2
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, K1, K2> Long unionAndStore(K k, K1 k1, K2 k2) {
        try {
            return opsForSet().unionAndStore(k, k1, k2);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取多个变量的合集并保存到最后一个参数上
     *
     * @param k
     * @param k1
     * @param k2
     * @param <K>
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <K, K1, K2> Long unionAndStore(K k, Collection<K1> k1, K2 k2) {
        try {
            return opsForSet().unionAndStore(k, k1, k2);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
