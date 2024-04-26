package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    int count = 0;

    public SimpleBlockingQueue() {
        this.count = 10;
    }

    public SimpleBlockingQueue(int count) {
        this.count = count;
    }

    public synchronized void offer(T value) {
        if (queue.size() < count) {
            queue.offer(value);
            notifyAll();
        }
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            this.wait();
        }
        T element = queue.poll();
        notifyAll();
        return element;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
