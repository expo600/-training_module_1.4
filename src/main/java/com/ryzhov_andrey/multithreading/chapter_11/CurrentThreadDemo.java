package main.java.com.ryzhov_andrey.multithreading.chapter_11;

public class CurrentThreadDemo {
    public static void main(String[] args) {
        // получаем ссылку на главный поток
        Thread thread = Thread.currentThread();
        System.out.println("Текущий поток исполнения: " + thread);

        // изменяем имя потока
        thread.setName("My Thread");
        System.out.println("После изменения имени потока: " + thread);

        try {
            for (int i = 5; i > 0; i--) {
                System.out.println(i);
                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            System.out.println("Главный поток исполнения прерван");
        }
    }
}
