package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * В SimpleBlockingQueue по умолчанию создается пул размером 10
 */

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    private final static int DEFAULT_POOL_SIZE = 10;

    private int count = 0;

    public SimpleBlockingQueue() {
        this.count = DEFAULT_POOL_SIZE;
    }

    public SimpleBlockingQueue(int count) {
        this.count = count;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= count) {
            this.wait();
        }
        queue.offer(value);
        notifyAll();
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
