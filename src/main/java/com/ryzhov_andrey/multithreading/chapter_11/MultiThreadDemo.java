package main.java.com.ryzhov_andrey.multithreading.chapter_11;

// создать несколько потоков исполнения
class NewThread_2 implements Runnable {
    String name; // имя потока исполнения
    Thread thread;

    NewThread_2(String threadName) {
        name = threadName;
        thread = new Thread(this, name);
        System.out.println("New thread: " + thread);
        thread.start(); // запустить новый поток на исполнение
    }

    // точка входа в поток исполнения
    @Override
    public void run() {
        try {
            for (int i = 5; i > 0; i--) {
                System.out.println(name + ": " + i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println(name + "thread aborted.");
        }
        System.out.println(name + "thread terminated.");
    }
}

public class MultiThreadDemo {
    public static void main(String[] args) {
        new NewThread_2("One");
        new NewThread_2("Two");
        new NewThread_2("Three");

        // ожидать завершение других потоков
        try {
            Thread.sleep(10000);
        }catch (InterruptedException e){
            System.out.println("Main thread interrupted.");
        }
        System.out.println("Main thread finished.");
    }
}
