package com.lnmj.common.utils;

import jdk.internal.dynalink.beans.StaticClass;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: panlin
 * @Date: 2020-01-20 17:00
 * @Description:
 */
public class ListPageUntil {
    public static Map listPage(List clubs, int pageSize, int pageIndex) {
        Map map = new HashMap();
        Paging paging = Paging.pagination(clubs.size(), pageSize, pageIndex);
        int fromIndex = paging.getQueryIndex();
        int toIndex = 0;
        if (fromIndex + paging.getPageSize() >= clubs.size()) {
            toIndex = clubs.size();
        } else {
            toIndex = fromIndex + paging.getPageSize();
        }
        if (fromIndex > toIndex) {
            map.put("list",Collections.EMPTY_LIST);
            map.put("total",0);
            return map;
        }

        map.put("list",clubs.subList(fromIndex, toIndex));
        map.put("total",paging.getTotalNum());
        return map;
    }
}
