package ru.job4j.threadlocal;

public class FirstThread  extends Thread {
    @Override
    public void run() {
        ThreadLocalDemo.threadLocal.set("Поток 1.");
        System.out.println(ThreadLocalDemo.threadLocal.get());
    }
}
