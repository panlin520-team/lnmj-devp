package com.lnmj.common.utils;

import java.util.Random;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2019/10/11
 */
public class CodeUtils {
    public static String getCharAndNumr(int length) {

        Random random = new Random();

        StringBuffer valSb = new StringBuffer();

        String charStr = "0123456789abcdefghijklmnopqrstuvwxyz";

        int charLength = charStr.length();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charLength);
            valSb.append(charStr.charAt(index));
        }
        return valSb.toString();
    }
}
