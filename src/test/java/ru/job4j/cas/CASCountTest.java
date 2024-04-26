package ru.job4j.cas;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {
    @Test
    public void whenCheckInitValue() {
        CASCount casCount = new CASCount();
        assertThat(casCount.get()).isEqualTo(0);
    }

    @Test
    public void when3CheckValue() {
        CASCount casCount = new CASCount();
        casCount.increment();
        assertThat(casCount.get()).isEqualTo(1);
        casCount.increment();
        assertThat(casCount.get()).isEqualTo(2);
        casCount.increment();
        assertThat(casCount.get()).isEqualTo(3);
    }

    @Test
    public void whenCreate3ThreadsAndCheckFinalValue() throws InterruptedException {
        CASCount casCount = new CASCount();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    casCount.increment();
                }
            }
        };
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        Thread thread3 = new Thread(runnable);
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
        assertThat(casCount.get()).isEqualTo(9);
    }
}