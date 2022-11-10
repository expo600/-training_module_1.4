package main.java.com.ryzhov_andrey.multithreading.chapter_28;

import java.util.concurrent.locks.ReentrantLock;

public class BlockDemo {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        new LockThread(lock, "A");
        new LockThread(lock, "B");
    }
}

// общий ресурс
class Shared_1 {
    static int count = 0;
}

// поток исполнения, инкрементирующий значение счетчика
class LockThread implements Runnable {
    String name;
    ReentrantLock lock;

    public LockThread(ReentrantLock lock, String name) {
        this.name = name;
        this.lock = lock;
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("Starting thread " + name);
        try {
            // сначала заблокировать счетчик
            System.out.println("Thread " + name + " waiting for counter lock");
            lock.lock();
            System.out.println("Thread " + name + " blocks the counter");
            Shared_1.count++;
            System.out.println("Thread " + name + ": " + Shared_1.count);

            // а теперь переключение контекста, если это возможно
            System.out.println("Thread " + name + " waiting");
            Thread.sleep(1000);
        }catch (InterruptedException e){
            System.out.println(e);
        } finally {
            // снять блокировку
            System.out.println("Thread " + name + " unlocks the counter");
            lock.unlock();
        }
    }
}
