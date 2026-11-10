package com.lesama.arr.lottery;

import java.util.*;

public class Lottery {

    /** 从 users 中随机抽 m 个不重复用户（会修改原数组） */
    public static List<Integer> drawByPartialShuffle(int[] users, int m) {
        if (users == null || m <= 0 || m > users.length) return new ArrayList<>();
        Random rand = new Random();
        int n = users.length;
        for (int i = 0; i < m; i++) {
            int j = i + rand.nextInt(n - i); // [i, n-1] 随机
            int tmp = users[i]; users[i] = users[j]; users[j] = tmp;
        }
        List<Integer> res = new ArrayList<>(m);
        for (int i = 0; i < m; i++) res.add(users[i]);
        return res;
    }

    public static List<Integer> drawByReservoir(Iterator<Integer> it, int m) {
        List<Integer> pool = new ArrayList<>(m);
        Random rand = new Random();
        int i = 0;
        while (it.hasNext()) {
            int x = it.next();
            if (i < m) {
                pool.add(x);
            } else {
                int j = rand.nextInt(i + 1); // [0, i]
                if (j < m) pool.set(j, x);   // 以 m/(i+1) 概率替换
            }
            i++;
        }
        return pool;
    }

    public static List<Integer> drawByFullShuffle(int[] users, int m) {
        if (users == null || m <= 0 || m > users.length) return new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        for (int u : users) list.add(u);
        Collections.shuffle(list);           // 全洗牌 O(n)
        return new ArrayList<>(list.subList(0, m));
    }

    public static List<Integer> drawBySet(int[] users, int m) {
        if (users == null || m <= 0 || m > users.length) return new ArrayList<>();
        int n = users.length;
        Set<Integer> picked = new HashSet<>();
        Random rand = new Random();
        while (picked.size() < m) {
            picked.add(rand.nextInt(n));
        }
        List<Integer> res = new ArrayList<>(m);
        for (int idx : picked) res.add(users[idx]);
        return res;
    }
}