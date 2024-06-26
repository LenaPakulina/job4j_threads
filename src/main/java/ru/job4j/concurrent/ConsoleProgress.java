package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        var process = new char[] {'-', '\\', '|', '/'};
        int index = 0;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("\rLoading : " + process[index++]);
                Thread.sleep(500);
                if (index >= process.length) {
                    index = 0;
                }
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            System.out.println(Thread.currentThread().getState());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }
}
