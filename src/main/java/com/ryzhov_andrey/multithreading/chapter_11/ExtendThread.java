package main.java.com.ryzhov_andrey.multithreading.chapter_11;

// создать второй поток исполнения, расширив класс Thread
class NewThread_1 extends Thread {

    NewThread_1() {
        // создать новый поток исполнения
        super("Демонстрационный поток");
        System.out.println("Дочерний поток: " + this);
        start(); // запустить поток
    }

    // точка входа во второй поток
    public void run() {
        try {
            for (int i = 5; i > 0; i--) {
                System.out.println("Дочерний поток: " + i);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println("Дочерний поток прерван: ");
        }
        System.out.println("Дочерний поток завершен.");
    }
}

public class ExtendThread {
    public static void main(String[] args) {
        // создать новый поток
        new NewThread_1();
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
