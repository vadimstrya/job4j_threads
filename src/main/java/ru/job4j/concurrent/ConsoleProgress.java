package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    private final char[] process = new char[]{'\\', '|', '/'};

    @Override
    public void run() {
        int i = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                System.out.print("\r load: " + process[i++]);
                Thread.sleep(100);
                if (i >= process.length) {
                    i = 0;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
        progress.join();
    }
}
