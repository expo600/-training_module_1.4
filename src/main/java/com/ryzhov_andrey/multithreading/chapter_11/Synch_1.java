package main.java.com.ryzhov_andrey.multithreading.chapter_11;

class CallMe_1 {
    void call(String str) {
        System.out.print("[" + str);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        System.out.println("]");
    }
}

class Caller_1 implements Runnable {
    String str;
    CallMe target;
    Thread thread;

    public Caller_1(CallMe targ, String s) {
        target = targ;
        str = s;
        thread = new Thread(this);
        thread.start();
    }

    @Override   // синхронизированный вызов метода call()
    public void run() {
        synchronized (target){   // синхронизированный блок
            target.call(str);
        }
    }
}

public class Synch_1 {
    public static void main(String[] args) {
        CallMe target = new CallMe();
        Caller ob1 = new Caller(target, "Welcome");
        Caller ob2 = new Caller(target, "into synchronized");
        Caller ob3 = new Caller(target, "world !");

        // ожидать завершения потока исполнения
        try {
            ob1.thread.join();
            ob2.thread.join();
            ob3.thread.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
    }
}
