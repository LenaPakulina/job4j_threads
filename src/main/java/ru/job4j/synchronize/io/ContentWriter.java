package ru.job4j.synchronize.io;

import java.io.*;
import java.util.function.Predicate;

public class ContentWriter {
    private final File file;

    public ContentWriter(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public synchronized void saveContent(String content) throws IOException {
        try (PrintWriter output = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(file)
                ))) {
            output.print(content);
        }
    }
}
