package ru.job4j.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String filename;
    public static final int ONE_SECOND = 1000;

    public Wget(String url, int speed, String filename) {
        this.url = url;
        this.speed = speed;
        this.filename = filename;
    }

    @Override
    public void run() {
        var file = new File(filename);
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            var dataBuffer = new byte[512];
            var startAt = System.currentTimeMillis();
            int bytesRead = 0;
            int allBytesRead = 0;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                allBytesRead += bytesRead;
                if (allBytesRead >= speed) {
                    var duration = System.currentTimeMillis() - startAt;
                    if (duration < ONE_SECOND) {
                        Thread.sleep(ONE_SECOND - duration);
                    }
                    allBytesRead = 0;
                    startAt = System.currentTimeMillis();
                }
                output.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3) {
            throw new IllegalArgumentException("Pass the url link and speed as arguments.");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String filename = args[2];
        Thread wget = new Thread(new Wget(url, speed, filename));
        wget.start();
        wget.join();
    }
}
