package ru.job4j.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;

public class FileDownload {
    public static void main(String[] args) throws IOException {
        var startAt = System.currentTimeMillis();
        var file = new File("tmp.xml");
        String url = "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";
        try (var input = new URL(url).openStream(); var output = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var data = new byte[512];
            int bytesRead = 0;
            while ((bytesRead = input.read(data, 0, data.length)) != -1) {
                var downloadAt = System.nanoTime();
                output.write(data, 0, bytesRead);
                System.out.println("Read 512 bytes : " + (System.nanoTime() - downloadAt) + " nano.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Files.size(file.toPath()) + " bytes");
    }
}
