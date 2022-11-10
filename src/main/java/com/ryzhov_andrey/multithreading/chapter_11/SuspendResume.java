package main.java.com.ryzhov_andrey.multithreading.chapter_11;

// приостановка и возобновление исполнения потока
class NewThread_4 implements Runnable {
    String name;  // имя потока исполнения
    Thread t;
    boolean suspendFlag;

    NewThread_4(String threadName) {
        name = threadName;
        t = new Thread(this, name);
        System.out.println("Новый поток: " + t);
        suspendFlag = false;
        t.start(); // запустить поток исполнения
    }

    // точка входа в поток исполнения
    @Override
    public void run() {
        try {
            for (int i = 15; i > 0; i--) {
                System.out.println(name + ": " + i);
                Thread.sleep(200);
                synchronized (this) {
                    while (suspendFlag) {
                        wait();
                    }
                }

            }
        } catch (InterruptedException e) {
            System.out.println(name + " прерван");
        }
        System.out.println(name + " завершен");
    }

    synchronized void mySuspend() {
        suspendFlag = true;
    }

    synchronized void myResume() {
        suspendFlag = false;
        notify();
    }
}

public class SuspendResume {
    public static void main(String[] args) {
        NewThread_4 od1 = new NewThread_4("Один");
        NewThread_4 od2 = new NewThread_4("Два");

        try {
            Thread.sleep(1000);
            od1.mySuspend();
            System.out.println("Приостановка потока Один");
            Thread.sleep(1000);
            od1.myResume();
            System.out.println("Возобновление потока Один");
            od2.mySuspend();
            System.out.println("Приостановка потока Два");
            Thread.sleep(1000);
            od2.myResume();
            System.out.println("Возобновление потока Два");
        } catch (InterruptedException e) {
            System.out.println("Главный поток прерван.");
        }
        // ожидать завершения потоков исполнения
        try {
            System.out.println("Ожидание завершения потоков");
            od1.t.join();
            od2.t.join();
        } catch (InterruptedException e) {
            System.out.println("Главный поток прерван.");
        }
        System.out.println("Главный поток завершен.");
    }
}
