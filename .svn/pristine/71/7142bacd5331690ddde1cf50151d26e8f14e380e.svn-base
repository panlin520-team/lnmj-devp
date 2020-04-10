package com.lnmj.common.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Auther: panlin
 * @Date: 2019/5/5 13:59
 * @Description:
 */
public class accountNameUtil {
    public static String generateWord() {
        String[] beforeShuffle = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i",
                "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        List list = Arrays.asList(beforeShuffle);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        String afterShuffle = sb.toString();
        String result = afterShuffle.substring(5, 9);
        return result;
    }



}
