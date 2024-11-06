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
    private final String path;

    public Wget(String url, int speed, String path) {
        this.url = url;
        this.speed = speed;
        this.path = path;
    }

    @Override
    public void run() {
        var file = new File(path);
        var bufferSize = 512;

        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            var dataBuffer = new byte[bufferSize];
            var bytesRead = 0;
            var downloadByte = 0;
            var downloadStart = System.currentTimeMillis();

            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                output.write(dataBuffer, 0, bytesRead);
                downloadByte += bytesRead;

                if (downloadByte >= speed) {
                    var downloadTime = System.currentTimeMillis() - downloadStart;
                    System.out.println("Загружено " + downloadByte + " byte за " + downloadTime + " ms");

                    if (downloadTime < 1000) {
                        var pauseTime = 1000 - downloadTime;
                        System.out.println("Пауза на " + pauseTime + " ms");
                        Thread.sleep(pauseTime);
                    }
                    downloadByte = 0;
                    downloadStart = System.currentTimeMillis();
                }
            }
            System.out.println("Размер загруженного файла: " + Files.size(file.toPath()) + " bytes");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void validateArgs(String[] args) {
        if (args == null || args.length < 3) {
            throw new RuntimeException(String.format("Укажите аргументы запуска в формате: [%s] [%s] [%s]",
                    "URL", "Скорость загрузки, byte/sec", "Путь сохранения файла"));
        }

        String url = args[0];
        if (!UrlValidator.getInstance().isValid(url)) {
            throw new RuntimeException("Укажите валидный URL");
        }

        int speed = Integer.parseInt(args[1]);
        if (speed <= 0) {
            throw new RuntimeException("Скорость загрузки должна быть больше 0");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validateArgs(args);

        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String path = args[2];
        Thread wget = new Thread(new Wget(url, speed, path));
        wget.start();
        wget.join();
    }
}
