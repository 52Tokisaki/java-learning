package com.lesama.arr.cubes;

public class MinCubesTest {
    static void check(String name, int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError(name + " 期望 " + expected + "，实际 " + actual);
        }
        System.out.println("PASS  " + name + " = " + actual);
    }

    public static void main(String[] args) {
        // 基本：1,2,3 -> 1 装 2，2 装 3？注意 2>1 可装，3>2 可装，
        // 但一个只能装一个：配对 (1->2) 或 (2->3)，最多 1 对，答案 3-1=2
        check("[1,2,3]", 2, MinCubes.minOuterCount(new int[]{1, 2, 3}));

        // 1,2,3,4 -> 可配对 1->2, 3->4 或 2->3 等，最多 2 对，答案 2
        check("[1,2,3,4]", 2, MinCubes.minOuterCount(new int[]{1, 2, 3, 4}));

        // 全相等不能互装，答案 4
        check("[5,5,5,5]", 4, MinCubes.minOuterCount(new int[]{5, 5, 5, 5}));

        // 单元素
        check("[7]", 1, MinCubes.minOuterCount(new int[]{7}));

        // 空数组
        check("[]", 0, MinCubes.minOuterCount(new int[]{}));

        // 1,1,2,3 -> 1->2, 1->3 可配 2 对？注意每个大只能接一个：
        // 排序 1,1,2,3；i=0,j=2 (2>1) 配 1->2；i=1,j=3 (3>1) 配 1->3，共 2 对，答案 4-2=2
        check("[1,1,2,3]", 1, MinCubes.minOuterCount(new int[]{1, 1, 2, 3}));

        System.out.println("\n全部测试通过 ✅");
    }
}