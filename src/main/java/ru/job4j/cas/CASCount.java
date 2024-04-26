package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int currValue;
        int temp;
        do {
            currValue = count.get();
            temp = currValue + 1;
        } while (!count.compareAndSet(currValue, temp));
    }

    public int get() {
        return count.get();
    }
}