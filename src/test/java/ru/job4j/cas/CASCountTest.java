package ru.job4j.cas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CASCountTest {

    @Test
    public void whenCountInTwoThread() throws InterruptedException {
        CASCount count = new CASCount();
        int incrementCount = 100;

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < incrementCount; i++) {
                count.increment();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < incrementCount; i++) {
                count.increment();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        assertEquals(incrementCount * 2, count.get());
    }
}
