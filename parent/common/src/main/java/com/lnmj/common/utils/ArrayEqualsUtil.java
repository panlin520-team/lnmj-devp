package com.lnmj.common.utils;


import com.lnmj.common.config.WechatConfig;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: panlin
 * @Date: 2019/4/24 16:47
 * @Description:
 */
public class ArrayEqualsUtil {

    public static boolean equals(String[] list1,String[] list2) {
        Arrays.sort(list1);
        Arrays.sort(list2);
        return Arrays.equals(list1, list2);
    }
}
