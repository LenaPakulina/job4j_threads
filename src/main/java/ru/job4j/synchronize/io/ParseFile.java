package ru.job4j.synchronize.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public synchronized String getContent(Predicate<Character> filter) throws IOException {
        StringBuilder result = new StringBuilder();
        try (var stream = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = stream.read()) > 0) {
                if (filter.test((char) data)) {
                    result.append((char) data);
                }
            }
        }
        return result.toString();
    }
}
