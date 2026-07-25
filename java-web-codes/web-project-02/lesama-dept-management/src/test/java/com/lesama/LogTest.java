package com.lesama;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {
    private static final Logger log = LoggerFactory.getLogger(LogTest.class);

    @Test
    public void test() {
        log.debug("开始测试");

        // 定义一个数组，统计其总和
        int[] arr = {1, 2, 3, 4, 5};
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }

        log.info("数组总和为：{}", sum);

        log.debug("测试结束");
    }
}
