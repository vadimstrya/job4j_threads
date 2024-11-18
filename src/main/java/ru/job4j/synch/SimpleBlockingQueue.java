package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue;

    private final int maxSize;

    public SimpleBlockingQueue(int maxSize) {
        this.maxSize = maxSize;
        this.queue = new LinkedList<>();
    }

    /** Добавление объекта в очередь */
    public void offer(T value) throws InterruptedException {
        synchronized (queue) {
            while (maxSize == queue.size()) {
                queue.wait();
            }
            queue.offer(value);
            queue.notifyAll();
        }
    }

    /** Извлечение объекта из очереди */
    public T poll() throws InterruptedException {
        synchronized (queue) {
            while (queue.isEmpty()) {
                queue.wait();
            }
            T result = queue.poll();
            queue.notifyAll();
            return result;
        }
    }
}
