package main.java.com.ryzhov_andrey.multithreading.chapter_28;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo {
    public static void main(String[] args) {
        new AtomThread("A");
        new AtomThread("B");
        new AtomThread("C");
    }
}

// общий ресурс
class Shared_2 {
    static AtomicInteger ai = new AtomicInteger(0);
}

// поток исполнения, инкрементирующий значение счетчика
class AtomThread implements Runnable {
    String name;

    public AtomThread(String name) {
        this.name = name;
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("Starting thread " + name);
        for (int i = 1; i <= 3; i++) {
            System.out.println("Thread " + name + " received: " + Shared_2.ai.getAndSet(i));

        }
    }
}