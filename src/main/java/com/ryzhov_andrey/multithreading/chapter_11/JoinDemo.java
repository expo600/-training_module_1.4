package main.java.com.ryzhov_andrey.multithreading.chapter_11;

// применить метод Join(), чтобы ожидать завершения потоков исполнения
class NewThread_3 implements Runnable {

    String name; // имя потока исполнения
    Thread thread;

    NewThread_3(String threadName) {
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


public class JoinDemo {
    public static void main(String[] args) {

        NewThread_3 ob1 = new NewThread_3("One");
        NewThread_3 ob2 = new NewThread_3("Two");
        NewThread_3 ob3 = new NewThread_3("Tree");

        System.out.println("Thread One started: "+ob1.thread.isAlive());
        System.out.println("Thread Two started: "+ob2.thread.isAlive());
        System.out.println("Thread Thee started: "+ob3.thread.isAlive());

        // ожидать завершения исполнения
        try {
            System.out.println("Waiting for threads to complete");
            ob1.thread.join();
            ob2.thread.join();
            ob3.thread.join();
        }catch (InterruptedException e){
            System.out.println("Main thread interrupted.");
        }
        System.out.println("Thread One started: "+ob1.thread.isAlive());
        System.out.println("Thread Two started: "+ob2.thread.isAlive());
        System.out.println("Thread Thee started: "+ob3.thread.isAlive());
        System.out.println("Main thread finished.");
    }
}
