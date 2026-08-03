package com.lesama;

public class ThreadLocalTest {
    public static void main(String[] args) {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        threadLocal.set("lesama");
        new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal.set("sub lesama");
                System.out.println(Thread.currentThread().getName() + threadLocal.get());
            }
        }).start();
        System.out.println(threadLocal.get());
        threadLocal.remove();
    }
}
