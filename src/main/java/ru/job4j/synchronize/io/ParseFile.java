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

    private synchronized String parse(Predicate<Character> filter) throws IOException {
        StringBuilder result = new StringBuilder();
        try (var stream = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = stream.read()) != -1) {
                if (filter.test((char) data)) {
                    result.append((char) data);
                }
            }
        }
        return result.toString();
    }

    public String getContent() throws IOException {
        return parse(character -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return parse(character -> (character < 0x80));
    }
}
