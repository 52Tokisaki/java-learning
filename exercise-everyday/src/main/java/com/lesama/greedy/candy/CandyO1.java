package com.lesama.greedy.candy;

public class CandyO1 {
    public static int candy(int[] ratings) {
        if (ratings == null || ratings.length == 0) return 0;
        int n = ratings.length;
        int total = 1;   // 第一个孩子先给 1 颗
        int up = 0;      // 当前连续上升长度
        int down = 0;    // 当前连续下降长度
        int peak = 0;    // 上一个上升段的峰值长度

        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                up++;
                down = 0;
                peak = up;
                total += 1 + up;          // 上升段每个孩子比前一个多 1
            } else if (ratings[i] < ratings[i - 1]) {
                down++;
                up = 0;
                total += down;            // 下降段每个孩子至少 1，逐级加
                if (down > peak) {        // 下降段超过峰值，峰值需补 1
                    total += 1;
                }
            } else {
                // 评分相等，重置，当前孩子给 1 颗
                up = 0;
                down = 0;
                peak = 0;
                total += 1;
            }
        }
        return total;
    }
}
