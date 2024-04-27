package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    private int count = 0;

    public SimpleBlockingQueue() {
        this.count = new Random().nextInt(2, 10);
    }

    public SimpleBlockingQueue(int count) {
        this.count = count;
    }

    public synchronized void offer(T value) {
        while (queue.size() >= count) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
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
