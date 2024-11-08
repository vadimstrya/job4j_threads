package ru.job4j.thread.file;

import java.io.*;

public class SaveFile {

    private final File file;

    public SaveFile(File file) {
        this.file = file;
    }

    public void saveContent(String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file), 1024)) {
            writer.write(content);
        }
    }
}
