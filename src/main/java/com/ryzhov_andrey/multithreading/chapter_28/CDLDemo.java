package main.java.com.ryzhov_andrey.multithreading.chapter_28;

// продемонстрировать применение класса CountDownLatch


import java.util.concurrent.CountDownLatch;

public class CDLDemo {
    public static void main(String[] args) {
        CountDownLatch cdl = new CountDownLatch(5);
        System.out.println("«апуск потока исполнени€");

         new MyThread(cdl);
         try {
             cdl.await();
         }catch (InterruptedException e){
             System.out.println(e);
         }
        System.out.println("«авершение потока исполнени€");
    }
}
 class MyThread implements Runnable{
    CountDownLatch latch;


     public MyThread(CountDownLatch latch) {
         this.latch = latch;
         new Thread(this).start();
     }

     @Override
     public void run() {
         for (int i = 0; i < 5; i++) {
             System.out.println(i);
             latch.countDown(); // ќбратный отсчет
         }
     }
 }