package main.java.com.ryzhov_andrey.multithreading.chapter_28;

import java.util.concurrent.Phaser;

public class PhaserDemo {
    public static void main(String[] args) {
        Phaser phsr = new Phaser(1);
        int curPhase;
        System.out.println("Running threads");
        new MyThread_5(phsr, "A");
        new MyThread_5(phsr, "B");
        new MyThread_5(phsr, "C");

        // ожидать всеми потоками завершения первой фазы
        curPhase = phsr.getPhase();
        phsr.arriveAndAwaitAdvance();
        System.out.println("Phase " + curPhase + " completed.");

        // ожидать всеми потоками завершения второй фазы
        curPhase = phsr.getPhase();
        phsr.arriveAndAwaitAdvance();
        System.out.println("Phase " + curPhase + " completed.");

        curPhase = phsr.getPhase();
        phsr.arriveAndAwaitAdvance();
        System.out.println("Phase " + curPhase + " completed.");

        // снять основной поток исполнения с регистрации
        phsr.arriveAndDeregister();

        if (phsr.isTerminated())
            System.out.println("Phase synchronizer completed"); // Синхронизатор фаз завершен
    }
}

// поток исполнения использующий синхронизатор фаз типа Phaser
class MyThread_5 implements Runnable {
    Phaser phaser;
    String name;

    public MyThread_5(Phaser phaser, String name) {
        this.phaser = phaser;
        this.name = name;
        phaser.register();
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("Thread " + name + " starts the first phase");
        phaser.arriveAndAwaitAdvance();  // известить о достижении фазы

        // Небольшая пауза, чтобы не нарушить порядок ввода
        // Только для иллюстрации, но не обязательно для правильного функционирования синхронизатора фаз
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("Thread " + name + " starts the second phase");
        phaser.arriveAndAwaitAdvance();  // известить о достижении фазы

        // Небольшая пауза, чтобы не нарушить порядок ввода
        // Только для иллюстрации, но не обязательно для правильного функционирования синхронизатора фаз
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("Thread " + name + " starts the second phase");
        phaser.arriveAndAwaitAdvance();  // известить о достижении фазы и снять поток с регистрации
    }
}
