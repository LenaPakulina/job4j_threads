package ru.job4j.pool;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.*;

public class ThreadPoolTest {
    @Test
    public void whenThreadPoolWork() throws InterruptedException {
        AtomicInteger value = new AtomicInteger(0);
        ThreadPool threadPool = new ThreadPool();
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                threadPool.work(value::incrementAndGet);
            }
        });
        thread.start();
        thread.join();
        threadPool.shutdown();
        assertThat(value.get()).isEqualTo(100);
    }
}