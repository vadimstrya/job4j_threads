package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {

    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int i;
        do {
            i = get();
        } while (!count.compareAndSet(i, i + 1));
    }

    public int get() {
        return count.get();
    }
}
