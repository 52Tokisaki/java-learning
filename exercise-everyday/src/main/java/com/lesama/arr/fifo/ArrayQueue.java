package com.lesama.arr.fifo;

public class ArrayQueue<E> {
    private Object[] data;
    private int head;   // 队头下标
    private int tail;   // 队尾下一个可写位置
    private int size;   // 当前元素个数
    private int cap;    // 容量

    public ArrayQueue(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("容量必须为正");
        this.cap = capacity;
        this.data = new Object[capacity];
        this.head = 0;
        this.tail = 0;
        this.size = 0;
    }

    // 入队，队满返回 false
    public boolean enqueue(E e) {
        if (size == cap) return false;      // 队满
        data[tail] = e;
        tail = (tail + 1) % cap;            // 循环后移
        size++;
        return true;
    }

    // 出队，队空返回 null
    @SuppressWarnings("unchecked")
    public E dequeue() {
        if (size == 0) return null;         // 队空
        E e = (E) data[head];
        data[head] = null;                  // 帮助 GC
        head = (head + 1) % cap;            // 循环后移
        size--;
        return e;
    }

    // 查看队头，不出队
    @SuppressWarnings("unchecked")
    public E peek() {
        if (size == 0) return null;
        return (E) data[head];
    }

    public boolean isEmpty() { return size == 0; }
    public boolean isFull()  { return size == cap; }
    public int size()        { return size; }
}