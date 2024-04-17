package ru.job4j.concurrent;

import java.sql.SQLOutput;

public class Wget {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        int index = 0;
                        while (index <= 100) {
                            System.out.print("\rLoading : " + index++ + "%");
                            Thread.sleep(100);
                        }
                        System.out.print(System.lineSeparator() + "Loaded.");
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
        );
        thread.start();
    }
}
