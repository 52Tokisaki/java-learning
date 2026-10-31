package com.lesama.knapsack.completeknapsack;

public class CompleteKnapsackTest {
    static void check(String name, Object expected, Object actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError(name + " 期望 " + expected + "，实际 " + actual);
        }
        System.out.println("PASS  " + name + " = " + actual);
    }

    public static void main(String[] args) {
        int[] w = {1, 2, 5};

        // 能否凑出
        check("能否凑 11", true, CompleteKnapsack.canMake(w, 11));
        check("能否凑 3", true, CompleteKnapsack.canMake(w, 3));
        check("空砝码凑 0", true, CompleteKnapsack.canMake(new int[]{}, 0));
        check("空砝码凑 5", false, CompleteKnapsack.canMake(new int[]{}, 5));

        // 最少个数（LeetCode 322：coins=[1,2,5], amount=11 -> 3）
        check("最少凑 11", 3, CompleteKnapsack.minCount(w, 11));
        check("最少凑 0", 0, CompleteKnapsack.minCount(w, 0));
        check("凑不出 3（砝码[2]）", -1, CompleteKnapsack.minCount(new int[]{2}, 3));

        // 方案数（组合，LeetCode 518：coins=[1,2,5], amount=5 -> 4）
        // 4 种：5；2+2+1；2+1+1+1；1*5
        check("组合数凑 5", 4, CompleteKnapsack.countWays(w, 5));
        check("组合数凑 0", 1, CompleteKnapsack.countWays(w, 0));
        check("组合数凑 3（砝码[2]）", 0, CompleteKnapsack.countWays(new int[]{2}, 3));

        // 排列数（考虑顺序，LeetCode 377：nums=[1,2], target=3 -> 3）
        // 3 种：1+1+1；1+2；2+1
        check("排列数凑 3（[1,2]）", 3, CompleteKnapsack.countPermutations(new int[]{1, 2}, 3));

        System.out.println("\n全部测试通过 ✅");
    }
}