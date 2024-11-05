package ru.job4j.thread;

import org.apache.commons.validator.routines.UrlValidator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class Wget implements Runnable {

    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        var file = new File("tmp.xml");
        var bufferSize = 512;

        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[bufferSize];
            int bytesRead;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                var downloadAt = System.nanoTime();
                output.write(dataBuffer, 0, bytesRead);
                var downloadTime = System.nanoTime() - downloadAt;
                var downloadSpeed = (int) ((double) bufferSize / downloadTime * 1_000_000);
                System.out.println("Read " + bufferSize + " bytes: time = " + downloadTime + " nanosecond, speed = " + downloadSpeed + " byte/millisecond");

                if (downloadSpeed > speed) {
                    var sleepTime = (int) ((double) downloadSpeed / speed);
                    System.out.println("Thread sleep on " + sleepTime + " millisecond");
                    Thread.sleep(sleepTime);
                }
            }
            System.out.println(Files.size(file.toPath()) + " bytes");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);

        if (!UrlValidator.getInstance().isValid(url)) {
            System.out.println("Введите валидный URL");
        } else if (speed <= 0) {
            System.out.println("Скорость загрузки должна быть больше 0");
        } else {
            Thread wget = new Thread(new Wget(url, speed));
            wget.start();
            wget.join();
        }
    }
}
