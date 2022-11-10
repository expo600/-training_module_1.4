package main.java.com.ryzhov_andrey.multithreading.chapter_28;

import java.util.concurrent.Semaphore;

public class SemDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);

        new IncThread(semaphore, "A");
        new DecThread(semaphore, "B");
    }
}

// общий ресурс
class Shared {
    static int count = 0;
}

// поток исполнения увеличивающий значение счетчика на 1
class IncThread implements Runnable {
    String name;
    Semaphore semaphore;

    IncThread(Semaphore sem, String str) {
        semaphore = sem;
        name = str;
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("Запуск потока " + name);
        try {
            // сначала получить разрешение
            System.out.println("Поток " + name + " ожидает разрешения");
            semaphore.acquire();
            System.out.println("Поток " + name + " получает разрешение");

            // а теперь получить доступ к общему ресурсу
            for (int i = 0; i < 5; i++) {
                Shared.count++;
                System.out.println(name + ": " + Shared.count);

                // разрешить если возможно переключение контекста
                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        // освободить разрешение
        System.out.println("Поток " + name + " освобождает разрешение");
        semaphore.release();
    }
}

// поток исполнения уменьшающий значение счетчика на 1
class DecThread implements Runnable {
    String name;
    Semaphore semaphore;

    DecThread(Semaphore sem, String str) {
        semaphore = sem;
        name = str;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            // Сначала получить разрешение
            System.out.println("Поток " + name + " ожидает разрешения");
            semaphore.acquire();
            System.out.println("Поток " + name + " получает разрешение");

            // а теперь получить доступ к общему ресурсу
            for (int i = 0; i < 5; i++) {
                Shared.count--;
                System.out.println(name + ": " + Shared.count);

                // разрешить если возможно переключение контекста
                Thread.sleep(10);
            }
        }catch (InterruptedException e){
            System.out.println(e);
        }
        // освободить разрешение
        System.out.println("Поток " + name + " освобождает разрешение");
        semaphore.release();
    }
}