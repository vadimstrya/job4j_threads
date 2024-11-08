package ru.job4j.thread.file;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {

    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> filter) throws IOException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file), 1024)) {
            int data;
            while ((data = reader.read()) != -1) {
                char c = (char) data;
                if (filter.test(c)) {
                    builder.append(c);
                }
            }
        }
        return builder.toString();
    }
}
