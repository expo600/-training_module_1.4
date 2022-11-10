package main.java.com.ryzhov_andrey.multithreading.chapter_11;

// создать новый поток исполнения
class NewThread implements Runnable {
    Thread thread;

    NewThread() {
        // создать новый второй поток исполнения
        thread = new Thread(this, "Демонстрационный поток");
        System.out.println("Дочерный поток создан: " + thread);
        thread.start();
        // запустить поток исполнения
    }

    // точка входа во второй поток исполнения
    @Override
    public void run() {
        try {
            for (int i = 5; i > 0; i--) {
                System.out.println("Дочерний поток: " + i);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println("Дочерний поток завершен.");
        }
        System.out.println("Дочерний поток завершен.");
    }
}

public class ThreadDemo {
    public static void main(String[] args) {
        // создать новый поток
        new NewThread();
        try {
            for (int i = 5; i > 0; i--) {
                System.out.println("Главный поток: " + i);
                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            System.out.println("Главный поток исполнения прерван.");
        }
        System.out.println("Главный поток завершен.");
    }
}

