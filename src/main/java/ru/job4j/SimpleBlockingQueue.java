package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    public void offer(T value) {
        synchronized (this) {
            queue.offer(value);
            notifyAll();
        }
    }

    public T poll() throws InterruptedException {
        T element = null;
        while (element == null) {
            synchronized (queue) {
                element = queue.poll();
            }
            if (element == null) {
                synchronized (this) {
                    this.wait();
                }
            }
        }
        return element;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
