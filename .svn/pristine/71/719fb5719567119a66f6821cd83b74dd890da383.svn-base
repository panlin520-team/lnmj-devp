package com.lnmj.common.baseDao;

import java.util.List;

/**
 * @Auther: panlin
 * @Date: 2019/5/5 12:12
 * @Description:
 */
public interface IBaseDao {
    <T, E> E select(String namespaceAndId, T params);

    <E> E select(String namespaceAndId);

    <T, E> List<E> selectList(String namespaceAndId, T params);

    <E> List<E> selectList(String namespaceAndId);

    <T> int update(String namespaceAndId, T params);

    <T> List<Long> updateList(String namespaceAndId, List<T> list);

    <T> int insert(String namespaceAndId, T params);

    <T> List<Long> insertList(String namespaceAndId, List<T> list);

    <T> int delete(String namespaceAndId, T params);

    <T> List<Long> deleteList(String namespaceAndId, List<T> list);

    <T> void batchALL(String namespaceAndId, List<T> params, Integer bathcount);
}
