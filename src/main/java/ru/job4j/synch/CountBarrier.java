package ru.job4j.synch;

public class CountBarrier {

    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int count = 1_000_000;
        CountBarrier countBarrier = new CountBarrier(count);

        Thread master = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            for (int i = 0; i < count; i++) {
                countBarrier.count();
            }
            System.out.println(Thread.currentThread().getName() + " finish");

        }, "Master");

        Thread slave = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            countBarrier.await();
            System.out.println(Thread.currentThread().getName() + " finish");
        }, "Slave");

        slave.start();
        Thread.sleep(1000);
        master.start();
    }
}
