package com.lesama.arr.lottery;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class LotteryTest {
    static void check(String name, boolean ok) {
        if (!ok) throw new AssertionError(name + " 失败");
        System.out.println("PASS  " + name);
    }

    public static void main(String[] args) {
        int[] users = {10, 20, 30, 40, 50, 60, 70, 80};

        // 1. 部分洗牌
        List<Integer> r1 = Lottery.drawByPartialShuffle(users.clone(), 3);
        check("部分洗牌 抽3个", r1.size() == 3 && new HashSet<>(r1).size() == 3);

        // 2. 蓄水池
        List<Integer> r2 = Lottery.drawByReservoir(
                Arrays.stream(users).boxed().iterator(), 3);
        check("蓄水池 抽3个", r2.size() == 3 && new HashSet<>(r2).size() == 3);

        // 3. 全洗牌
        List<Integer> r3 = Lottery.drawByFullShuffle(users.clone(), 5);
        check("全洗牌 抽5个", r3.size() == 5 && new HashSet<>(r3).size() == 5);

        // 4. Set 法
        List<Integer> r4 = Lottery.drawBySet(users.clone(), 4);
        check("Set法 抽4个", r4.size() == 4 && new HashSet<>(r4).size() == 4);

        // 边界
        check("m=0", Lottery.drawByPartialShuffle(users.clone(), 0).isEmpty());
        check("m>n", Lottery.drawByPartialShuffle(users.clone(), 100).isEmpty());
        check("null", Lottery.drawByPartialShuffle(null, 2).isEmpty());

        // 等概率验证：抽1个，跑 10 万次，各用户频率应接近 1/8
        int[] freq = new int[8];
        int[] origin = {0,1,2,3,4,5,6,7};
        Random rand = new Random(42);
        int trials = 100000;
        for (int t = 0; t < trials; t++) {
            int[] copy = origin.clone();
            int j = rand.nextInt(8);
            int tmp = copy[0]; copy[0] = copy[j]; copy[j] = tmp;
            freq[copy[0]]++;
        }
        boolean uniform = true;
        for (int f : freq) {
            double p = (double) f / trials;
            if (Math.abs(p - 1.0 / 8) > 0.01) uniform = false;
        }
        check("等概率验证（抽1个，频率≈1/8）", uniform);

        System.out.println("\n全部测试通过 ✅");
    }
}