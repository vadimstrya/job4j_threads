package ru.job4j.synch;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SimpleBlockingQueueTest {

    @Test
    public void whenGetAllElements() throws InterruptedException {
        final int size = 1000;
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(size);
        List<Integer> result = new ArrayList<>();

        Thread producer = new Thread(() -> {
            for (int i = 0; i < size; i++) {
                try {
                    queue.offer(i);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < size; i++) {
                try {
                    result.add(queue.poll());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        assertEquals(result.size(), size);
    }
}
