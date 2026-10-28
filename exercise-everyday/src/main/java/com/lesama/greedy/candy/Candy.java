package com.lesama.greedy.candy;

import java.util.Arrays;

public class Candy {
    /**
     * LeetCode 135 分发糖果
     * 每个孩子至少 1 颗；相邻孩子中评分高的必须拿更多糖果。
     * 返回最少糖果总数。
     */
    public static int candy(int[] ratings) {
        if (ratings == null || ratings.length == 0) return 0;
        int n = ratings.length;

        int[] candy = new int[n];
        Arrays.fill(candy, 1);
        candy[0] = 1;
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candy[i] = candy[i - 1] + 1;
            }
        }

        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candy[i] = Math.max(candy[i], candy[i + 1] + 1);
            }
        }

        /*int[] left = new int[n];
        int[] right = new int[n];
        java.util.Arrays.fill(left, 1);
        java.util.Arrays.fill(right, 1);

        // 从左到右：保证右边评分高的比左边多
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1;
            }
        }

        // 从右到左：保证左边评分高的比右边多
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                right[i] = right[i + 1] + 1;
            }
        }

        // 每个孩子取左右约束的最大值
        int total = 0;
        for (int i = 0; i < n; i++) {
            total += Math.max(left[i], right[i]);
        }*/
        int total = 0;
        for (int count : candy) {
            total += count;
        }
        return total;
    }
}
