package main.java.com.ryzhov_andrey.multithreading.task;


import java.util.concurrent.CountDownLatch;

import static main.java.com.ryzhov_andrey.multithreading.task.MultiThreadEx.r;

public class MultiThreadEx {

    public static Runnable r;

    public static void main(String[] args) throws InterruptedException {

        Foo foo = new Foo();

        new Thread(new Thread_C("C", foo)).start();
        new Thread(new Thread_B("B", foo)).start();
        new Thread(new Thread_A("A", foo)).start();
    }
}

class Thread_A implements Runnable {
    private Foo foo;
    private String name;

    public Thread_A(String name, Foo foo) {
        this.name = name;
        this.foo = foo;
    }

    @Override
    public void run() {
        foo.first(r);
    }
}

class Thread_B implements Runnable {
    private Foo foo;
    private String name;

    public Thread_B(String name, Foo foo) {
        this.name = name;
        this.foo = foo;

    }

    @Override
    public void run() {
        foo.second(r);
    }
}

class Thread_C implements Runnable {
    private Foo foo;
    private String name;

    public Thread_C(String name, Foo foo) {
        this.name = name;
        this.foo = foo;
    }

    @Override
    public void run() {
        foo.third(r);
    }
}


class Foo {

    CountDownLatch firstFinished = new CountDownLatch(1);
    CountDownLatch secondFinished = new CountDownLatch(1);
    CountDownLatch thirdFinished = new CountDownLatch(1);

    public void first(Runnable r) {
        System.out.print("first");
        firstFinished.countDown();
    }

    public void second(Runnable r) {
        try {
            firstFinished.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("second");
        secondFinished.countDown();
    }

    public void third(Runnable r) {
        try {
            secondFinished.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("third");
        thirdFinished.countDown();
    }
}
