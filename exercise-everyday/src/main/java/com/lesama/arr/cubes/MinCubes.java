package com.lesama.arr.cubes;

import java.util.Arrays;

public class MinCubes {
    /**
     * 返回最少需要几个“最外层”正方体才能把所有正方体装下。
     * 规则：边长较小的正方体可以放入边长较大的正方体当中；
     * 每个正方体内部最多直接容纳一个正方体（套娃式）。
     */
    public static int minOuterCount(int[] sides) {
        if (sides == null || sides.length == 0) return 0;
        int n = sides.length;
        int[] a = sides.clone();
        Arrays.sort(a);          // 升序

        int i = 0;               // 待被装的小正方体
        int j = 0;               // 尝试当外层的大正方体
        while (j < n) {
            if (a[j] > a[i]) {   // 严格大于才能装进去
                i++;             // a[i] 被 a[j] 装下
            }
            j++;
        }
        // i 表示被装进别人里面的个数，剩下的必须自己当最外层
        return n - i;
    }
}