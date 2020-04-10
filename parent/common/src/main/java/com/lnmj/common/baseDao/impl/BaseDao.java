package com.lnmj.common.baseDao.impl;

import com.lnmj.common.baseDao.IBaseDao;
import com.lnmj.common.baseDao.IBaseDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Auther: panlin
 * @Date: 2019/5/5 12:11
 * @Description:
 */
@Repository("BaseDaoImpl")
@Scope("prototype")
public class BaseDao implements IBaseDao {
    private static final Logger log = LoggerFactory.getLogger(com.lnmj.common.baseDao.impl.BaseDao.class);
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public BaseDao() {
    }

    public <T, E> E select(String namespaceAndId, T params) {
        return params == null ? this.sqlSessionTemplate.selectOne(namespaceAndId) : this.sqlSessionTemplate.selectOne(namespaceAndId, params);
    }

    public <E> E select(String namespaceAndId) {
        return this.select(namespaceAndId, (Object) null);
    }

    public <T, E> List<E> selectList(String namespaceAndId, T params) {
        return params == null ? this.sqlSessionTemplate.selectList(namespaceAndId) : this.sqlSessionTemplate.selectList(namespaceAndId, params);
    }

    public <E> List<E> selectList(String namespaceAndId) {
        return this.selectList(namespaceAndId, (Object) null);
    }

    public <T> int update(String namespaceAndId, T params) {
        return params == null ? this.sqlSessionTemplate.update(namespaceAndId) : this.sqlSessionTemplate.update(namespaceAndId, params);
    }

    public <T> List<Long> updateList(String namespaceAndId, List<T> list) {
        try {
            if (list != null && !list.isEmpty()) {
                MappedStatement ms = this.sqlSessionTemplate.getConfiguration().getMappedStatement(namespaceAndId);
                SqlCommandType sqlCommandType = ms.getSqlCommandType();
                BoundSql boundSql = ms.getSqlSource().getBoundSql(list.get(0));
                String sql = boundSql.getSql();
                List<ParameterMapping> list2 = boundSql.getParameterMappings();
                Connection connection = this.sqlSessionTemplate.getConnection();
                PreparedStatement statement = null;
                if (sqlCommandType == SqlCommandType.INSERT) {
                    statement = connection.prepareStatement(sql, 1);
                } else {
                    statement = connection.prepareStatement(sql);
                }

                Iterator var10 = list.iterator();

                while (true) {
                    Object item;
                    int index;
                    do {
                        if (!var10.hasNext()) {
                            List<Long> resultList = new ArrayList();
                            int[] resultArray = statement.executeBatch();
                            if (sqlCommandType != SqlCommandType.INSERT) {
                                int[] var21 = resultArray;
                                index = resultArray.length;

                                for (int var25 = 0; var25 < index; ++var25) {
                                    int intval = var21[var25];
                                    resultList.add(Long.valueOf(intval + ""));
                                }
                            } else {
                                ResultSet resultSet = statement.getGeneratedKeys();

                                while (resultSet.next()) {
                                    resultList.add(resultSet.getLong(0));
                                }
                            }

                            return resultList;
                        }

                        item = var10.next();
                    } while (item != null);

                    if (item instanceof Map) {
                        Map<String, Object> map = (Map) item;

                        for (index = 0; index < list2.size(); ++index) {
                            ParameterMapping pm = (ParameterMapping) list2.get(index);
                            Object value = map.get(pm.getProperty());
                            statement.setObject(index + 1, value);
                        }
                    } else if (!(item instanceof Long) && !(item instanceof String) && !(item instanceof Integer)) {
                        for (index = 0; index < list2.size(); ++index) {
                            ParameterMapping pm = (ParameterMapping) list2.get(index);
                            String methodName = hump("get_" + pm.getProperty(), "_");
                            Method method = item.getClass().getMethod(methodName);
                            Object value = method.invoke(item);
                            statement.setObject(index + 1, value);
                        }
                    } else {
                        statement.setObject(1, item);
                    }

                    statement.addBatch();
                }
            } else {
                return null;
            }
        } catch (Exception var17) {
            throw new RuntimeException(var17.getMessage());
        }
    }

    public <T> int insert(String namespaceAndId, T params) {
        return this.update(namespaceAndId, params);
    }

    public <T> List<Long> insertList(String namespaceAndId, List<T> list) {
        return this.updateList(namespaceAndId, list);
    }

    public <T> int delete(String namespaceAndId, T params) {
        return this.update(namespaceAndId, params);
    }

    public <T> List<Long> deleteList(String namespaceAndId, List<T> list) {
        return this.updateList(namespaceAndId, list);
    }

    public <T> void batchALL(String namespaceAndId, List<T> list, Integer bathcount) {
        List<T> data = new ArrayList();

        for (int i = 0; i < list.size(); ++i) {
            data.add(list.get(i));
            if (data.size() == bathcount || i == list.size() - 1) {
                this.batchUtil(namespaceAndId, data);
                data.clear();
            }
        }

    }

    private <T> void batchUtil(String namespaceAndId, List<T> list) {
        try {
            if (list != null && !list.isEmpty()) {
                MappedStatement ms = this.sqlSessionTemplate.getConfiguration().getMappedStatement(namespaceAndId);
                SqlCommandType sqlCommandType = ms.getSqlCommandType();
                BoundSql boundSql = ms.getSqlSource().getBoundSql(list.get(0));
                String sql = boundSql.getSql();
                List<ParameterMapping> list2 = boundSql.getParameterMappings();
                Connection connection = this.sqlSessionTemplate.getSqlSessionFactory().openSession().getConnection();
                PreparedStatement statement = null;
                if (sqlCommandType == SqlCommandType.INSERT) {
                    statement = connection.prepareStatement(sql, 1);
                } else {
                    statement = connection.prepareStatement(sql);
                }

                sql = sql.replaceAll("\\n", "");
                sql = sql.replaceAll("\\t", "");
                sql = sql.replaceAll("[[ ]]{2,}", " ");
                log.info("==>  Preparing：" + sql);
                Iterator var10 = list.iterator();

                while (true) {
                    Object item;
                    int index;
                    do {
                        if (!var10.hasNext()) {
                            List<Long> resultList = new ArrayList();
                            int[] resultArray = statement.executeBatch();
                            if (sqlCommandType != SqlCommandType.INSERT) {
                                int[] var23 = resultArray;
                                int var26 = resultArray.length;

                                for (index = 0; index < var26; ++index) {
                                    int intval = var23[index];
                                    resultList.add(Long.valueOf(intval + ""));
                                }
                            } else {
                                ResultSet resultSet = statement.getGeneratedKeys();

                                while (resultSet.next()) {
                                    try {
                                        resultList.add(resultSet.getLong(1));
                                    } catch (Exception var19) {
                                        log.error("错误：" + var19.toString());
                                    }
                                }
                            }

                            return;
                        }

                        item = var10.next();
                    } while (item == null);

                    StringBuffer values = new StringBuffer();
                    ParameterMapping pm;
                    if (item instanceof Map) {
                        Map<String, Object> map = (Map) item;

                        for (index = 0; index < list2.size(); ++index) {
                            pm = (ParameterMapping) list2.get(index);
                            Object value = map.get(pm.getProperty());
                            values.append(value).append("(").append(value.getClass()).append("),");
                            statement.setObject(index + 1, value);
                        }
                    } else if (!(item instanceof Long) && !(item instanceof String) && !(item instanceof Integer)) {
                        List<String> params = new ArrayList();

                        for (index = 0; index < list2.size(); ++index) {
                            pm = (ParameterMapping) list2.get(index);
                            String methodName = hump("get_" + pm.getProperty(), "_");
                            Method method = item.getClass().getMethod(methodName);
                            Object value = method.invoke(item);
                            params.add(value.toString());
                            statement.setObject(index + 1, value);
                            values.append(value).append("(").append(StringUtils.substringAfterLast(value.getClass().toString(), ".")).append("),");
                        }
                    } else {
                        statement.setObject(1, item);
                        values.append(item).append("(").append(StringUtils.substringAfterLast(item.getClass().toString(), ".")).append("),");
                    }

                    statement.addBatch();
                    values.delete(values.length() - 1, values.length());
                    log.info("==> Parameters：" + values);
                }
            }
        } catch (Exception var20) {
            log.error("错误：" + var20.toString());
            throw new RuntimeException(var20.toString());
        }
    }

    protected <T> void printSql(String id, T params) {
        try {
            MappedStatement ms = this.sqlSessionTemplate.getConfiguration().getMappedStatement(id);
            BoundSql boundSql = ms.getSqlSource().getBoundSql(params);
            String sql = boundSql.getSql();
            sql = sql.replaceAll("\\n", "");
            sql = sql.replaceAll("\\t", "");
            sql = sql.replaceAll("[[ ]]{2,}", " ");
            List<ParameterMapping> list2 = boundSql.getParameterMappings();
            if (params != null) {
                if (params instanceof Map) {
                    Map<String, Object> map = (Map) params;

                    for (int index = 0; index < list2.size(); ++index) {
                        ParameterMapping pm = (ParameterMapping) list2.get(index);
                        Object value = map.get(pm.getProperty());
                        sql = sql.replaceFirst("[?]", value + "");
                    }
                } else if (!(params instanceof Long) && !(params instanceof String) && !(params instanceof Integer)) {
                    for (int index = 0; index < list2.size(); ++index) {
                        ParameterMapping pm = (ParameterMapping) list2.get(index);
                        String methodName = hump("get_" + pm.getProperty(), "_");
                        Method method = params.getClass().getMethod(methodName);
                        Object value = method.invoke(params);
                        sql = sql.replaceFirst("[?]", value + "");
                    }
                } else {
                    sql = sql.replaceFirst("[?]", params + "");
                }
            }

            log.info(sql);
        } catch (Exception var12) {
            var12.printStackTrace();
        }

    }

    private static String hump(String methodName, String regex) {
        String[] strs = methodName.split(regex);
        StringBuffer str = new StringBuffer(100);
        str.append(strs[0]);
        str.append(strs[1].substring(0, 1).toUpperCase()).append(strs[1].substring(1));
        return str.toString();
    }
}
