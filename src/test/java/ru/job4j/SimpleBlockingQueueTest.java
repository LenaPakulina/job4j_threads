package ru.job4j;

import org.junit.jupiter.api.Test;
import ru.job4j.synchronize.threadsafe.SingleLockList;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;

class SimpleBlockingQueueTest {
    @Test
    public void checkPoll() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        final Integer[] result = new Integer[1];
        Thread producer  = new Thread(() -> queue.offer(5));
        Thread consumer = new Thread(() -> {
            try {
                result[0] = queue.poll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        producer.join();
        consumer.start();
        consumer.join();
        assertThat(result[0]).isEqualTo(5);
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        List<Integer> buffer = new LinkedList<>();
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                queue.offer(i);
            }
        });
        Thread consumer = new Thread(() -> {
            while (buffer.size() < 5) {
                try {
                    buffer.add(queue.poll());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertEquals(Arrays.asList(0, 1, 2, 3, 4), buffer);
    }
}