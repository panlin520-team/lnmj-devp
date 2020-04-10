package com.lnmj.common.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Auther: panlin
 * @Date: 2019/5/5 13:59
 * @Description:
 */
public class MemoContinuousCheckUtil {
    /**
     * 如果没有0的存在，要组成连续的数列，最大值和最小值的差距必须是4
     * 存在0的情况下，只要最大值和最小值的差距小于4就可以了，
     * 所以应找出数列中非0的最大值和非0的最小值，时间复杂度为O(n)
     *
     * @param a
     * @return
     */
    public static boolean IsContinous(int[] a) {
        int len = a.length;
        int min = -1, max = -1;
        for (int i = 0; i < len; i++) {
            if (a[i] != 0) {
                if (min > a[i] || min == -1)
                    min = a[i];
                if (max < a[i] || max == -1)
                    max = a[i];
            }
        }//for
        if (max - min > len - 1)
            return false;
        else
            return true;
    }

    public static void main(String[] args) {
        int a[] = {1};
        if (IsContinous(a)) {
            System.out.println("相邻");
        } else
            System.out.println("不相邻");
    }
}
