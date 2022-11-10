package main.java.com.ryzhov_andrey.multithreading.chapter_28;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class BarDemo {
    public static void main(String[] args) {
        CyclicBarrier cb = new CyclicBarrier(3,new BarAction());

        System.out.println("Running threads"); // запуск потоков
        new MyThread_1(cb,"A");
        new MyThread_1(cb,"B");
        new MyThread_1(cb,"C");
        new MyThread_1(cb,"X");
        new MyThread_1(cb,"Y");
        new MyThread_1(cb,"Z");
    }
}

// поток исполнения, использующий барьер типа CyclicBarrier
class MyThread_1 implements Runnable{
    CyclicBarrier cbar;
    String name;

    public MyThread_1(CyclicBarrier cbar, String name) {
        this.cbar = cbar;
        this.name = name;
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println(name);
        try {
            cbar.await();
        }catch (BrokenBarrierException e){
            System.out.println(e);
        }catch (InterruptedException e){
            System.out.println(e);
        }
    }
}

// объект этого класса вызывается при достижении барьера типа CyclicBarrier
class BarAction implements Runnable{
    public void run() {
        System.out.println("Barrier reached");  // барьер достигнут
    }
}
