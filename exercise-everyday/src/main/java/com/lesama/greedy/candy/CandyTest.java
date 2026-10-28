package com.lesama.greedy.candy;

public class CandyTest {

    // 简单断言：不相等就打印并抛出异常
    static void check(String name, int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError(name + " 期望 " + expected + "，实际 " + actual);
        }
        System.out.println("PASS  " + name + " = " + actual);
    }

    public static void main(String[] args) {
        // 官方示例
        check("示例1 [1,0,2]", 5, Candy.candy(new int[]{1, 0, 2}));
        check("示例2 [1,2,2]", 4, Candy.candy(new int[]{1, 2, 2}));

        // 边界
        check("单个孩子 [5]", 1, Candy.candy(new int[]{5}));
        check("空数组", 0, Candy.candy(new int[]{}));
        check("null", 0, Candy.candy(null));

        // 两个元素
        check("相等 [1,1]", 2, Candy.candy(new int[]{1, 1}));
        check("升序 [1,2]", 3, Candy.candy(new int[]{1, 2}));
        check("降序 [2,1]", 3, Candy.candy(new int[]{2, 1}));

        // 单调
        check("严格递增 [1..5]", 15, Candy.candy(new int[]{1, 2, 3, 4, 5}));
        check("严格递减 [5..1]", 15, Candy.candy(new int[]{5, 4, 3, 2, 1}));
        check("全相等 [3,3,3,3]", 4, Candy.candy(new int[]{3, 3, 3, 3}));

        // 峰谷形状
        check("谷形 [5,4,1,2,3]", 11, Candy.candy(new int[]{5, 4, 1, 2, 3}));
        check("峰形 [1,3,4,2,1]", 9, Candy.candy(new int[]{1, 3, 4, 2, 1}));

        // 优化版 O(1) 对拍几个关键用例
        check("O1 示例1", 5, CandyO1.candy(new int[]{1, 0, 2}));
        check("O1 示例2", 4, CandyO1.candy(new int[]{1, 2, 2}));
        check("O1 峰形", 9, CandyO1.candy(new int[]{1, 3, 4, 2, 1}));

        // 随机对拍：两种实现结果必须一致
        java.util.Random rand = new java.util.Random(42);
        for (int t = 0; t < 1000; t++) {
            int n = 1 + rand.nextInt(50);
            int[] r = new int[n];
            for (int i = 0; i < n; i++) r[i] = rand.nextInt(10);
            int a = Candy.candy(r);
            int b = CandyO1.candy(r);
            if (a != b) {
                throw new AssertionError("对拍失败 " + java.util.Arrays.toString(r)
                        + " 清晰版=" + a + " 优化版=" + b);
            }
        }
        System.out.println("PASS  随机对拍 1000 组一致");

        System.out.println("\n全部测试通过 ✅");
    }
}
