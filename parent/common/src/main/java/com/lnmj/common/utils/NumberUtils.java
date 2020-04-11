package com.lnmj.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 〈〉
 *
 * @Author panlin
 * @create 2019/5/23
 */

public class NumberUtils {

    public static String getRandomOrderNo() {
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String str = simpleDateFormat.format(date);
        int i = (int)(Math.random()*900 + 100);
        String myStr = Integer.toString(i);//生成3位数随机数
        return  str + myStr;
    }

    public static String getRandomOrderNoChild() {
        int i = (int)(Math.random()*900 + 100);
        String myStr = Integer.toString(i);//生成3位数随机数
        return  myStr;
    }

    public static String getRandomCouponsNo() {
        int i = (int)(Math.random()*900 + 10000);
        String myStr = Integer.toString(i);//生成5位数随机数
        return  myStr;
    }

    public static void main(String[] args) {
        System.out.println(getRandomOrderNoChild());

    }
}
