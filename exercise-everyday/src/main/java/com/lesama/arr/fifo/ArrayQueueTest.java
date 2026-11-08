package com.lesama.arr.fifo;

public class ArrayQueueTest {
    static void check(String name, Object expected, Object actual) {
        if (!java.util.Objects.equals(expected, actual)) {
            throw new AssertionError(name + " 期望 " + expected + "，实际 " + actual);
        }
        System.out.println("PASS  " + name + " = " + actual);
    }

    public static void main(String[] args) {
        ArrayQueue<Integer> q = new ArrayQueue<>(3);

        // 基本 FIFO
        check("空队出队", null, q.dequeue());
        check("入队 1", true, q.enqueue(1));
        check("入队 2", true, q.enqueue(2));
        check("入队 3", true, q.enqueue(3));
        check("队满再入队", false, q.enqueue(4));
        check("队头 peek", 1, q.peek());
        check("出队 1", 1, q.dequeue());
        check("出队 2", 2, q.dequeue());

        // 循环复用：此时 head 前移，再入队应写到前面空出的位置
        check("循环入队 4", true, q.enqueue(4));
        check("循环入队 5", true, q.enqueue(5));   // 容量3，当前 size=3 满
        check("队头 peek", 3, q.peek());
        check("出队 3", 3, q.dequeue());
        check("出队 4", 4, q.dequeue());
        check("出队 5", 5, q.dequeue());
        check("队空", true, q.isEmpty());

        System.out.println("\n全部测试通过 ✅");
    }
}