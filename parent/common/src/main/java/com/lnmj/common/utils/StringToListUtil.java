package com.lnmj.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: panlin
 * @Date: 2019/5/28 13:50
 * @Description:
 */
public class StringToListUtil {
    public static  List<String> StringToListUtil(String listStr) {
        List<String> list = new ArrayList<>();
        if (listStr!=null){
            if (listStr.indexOf(",")>0){
                String[] a = listStr.split(",");
                list = Arrays.asList(a);
            }else{
                list.add(listStr);
            }
        }

        System.out.println(list);
        return list;
    }
}
