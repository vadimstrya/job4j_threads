package ru.job4j.pool;

import ru.job4j.synch.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {

    private final int size = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(() -> {
                try {
                    tasks.poll().run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            thread.start();
            threads.add(thread);
        }
    }

    public void work(Runnable job) {
        try {
            tasks.offer(job);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        System.out.printf("ThreadPool (size: %d) start!%n", pool.size);

        for (int i = 1; i <= 32; i++) {
            final int taskNum = i;
            pool.work(() -> System.out.printf("%s processed task %d%n", Thread.currentThread().getName(), taskNum));
        }

        Thread.sleep(3000);
        System.out.println("ThreadPool shutdown!");
        pool.shutdown();
        System.out.println("ThreadPool finish!");
    }
}
