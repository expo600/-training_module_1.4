package main.java.com.ryzhov_andrey.multithreading.chapter_28;

// простой пример исполнителя

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorSimple {
    public static void main(String[] args) {
        CountDownLatch countDownLatch1 = new CountDownLatch(5);
        CountDownLatch countDownLatch2 = new CountDownLatch(5);
        CountDownLatch countDownLatch3 = new CountDownLatch(5);
        CountDownLatch countDownLatch4 = new CountDownLatch(5);
        ExecutorService es = Executors.newFixedThreadPool(2);

        System.out.println("Running threads");
        // запускать потоки исполнения
        es.execute(new MyThread_7(countDownLatch1, "A"));
        es.execute(new MyThread_7(countDownLatch2, "B"));
        es.execute(new MyThread_7(countDownLatch3, "C"));
        es.execute(new MyThread_7(countDownLatch4, "D"));

        try {
            countDownLatch1.await();
            countDownLatch2.await();
            countDownLatch3.await();
            countDownLatch4.await();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        es.shutdown();
        System.out.println("Terminating threads");
    }
}

class MyThread_7 implements Runnable {
    String name;
    CountDownLatch cdl;

    public MyThread_7(CountDownLatch cdl, String name) {
        this.name = name;
        this.cdl = cdl;
        new Thread(this);
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(name + ": " + i);
            cdl.countDown();
        }
    }
}