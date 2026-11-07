package com.lesama.str.longestnorepeatstr;

import java.util.HashMap;

public class LongestNoRepeatStr {
    public static void main(String[] args) {
        System.out.println(longestNoRepeatStr("abcabcbb"));
    }

    public static int longestNoRepeatStr(String s) {

        HashMap<Character, Integer> map = new HashMap<>();
        int maxLen = 0;
        int start = 0;
        for (int i = 0; i < s.length(); i++) {
            if (!map.containsKey(s.charAt(i))) {
                map.put(s.charAt(i), i);
                maxLen = Math.max(maxLen, i - start + 1);
            } else {
                start = Math.max(start, map.get(s.charAt(i)) + 1);
                map.put(s.charAt(i), i);
            }
        }
        return maxLen;
    }
}
