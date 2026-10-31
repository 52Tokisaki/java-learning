package com.lesama.knapsack.completeknapsack;

import java.util.Arrays;

public class CompleteKnapsack {

    // 1. 能否凑出 target（完全背包，布尔型）
    public static boolean canMake(int[] weights, int target) {
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
        // 完全背包：容量正序遍历
        for (int w : weights) {
            for (int j = w; j <= target; j++) {
                dp[j] = dp[j] || dp[j - w];
            }
        }
        return dp[target];
    }

    // 2. 凑出 target 的最少砝码数，凑不出返回 -1
    public static int minCount(int[] weights, int target) {
        int INF = target + 1; // 用不可能的大值代替 Integer.MAX_VALUE 防溢出
        int[] dp = new int[target + 1];
        Arrays.fill(dp, INF);
        dp[0] = 0;
        for (int w : weights) {
            for (int j = w; j <= target; j++) {
                dp[j] = Math.min(dp[j], dp[j - w] + 1);
            }
        }
        return dp[target] == INF ? -1 : dp[target];
    }

    // 3. 凑出 target 的方案数（组合数，不考虑顺序）
    public static int countWays(int[] weights, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        // 外层物品、内层容量正序 => 组合数（不重复计数）
        for (int w : weights) {
            for (int j = w; j <= target; j++) {
                dp[j] += dp[j - w];
            }
        }
        return dp[target];
    }

    // 4. 凑出 target 的方案数（排列数，考虑顺序）—— 外层容量、内层物品
    public static int countPermutations(int[] weights, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int j = 1; j <= target; j++) {
            for (int w : weights) {
                if (j >= w) {
                    dp[j] += dp[j - w];
                }
            }
        }
        return dp[target];
    }
}