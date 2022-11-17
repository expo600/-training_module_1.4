package main.java.com.ryzhov_andrey.multithreading.task;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static main.java.com.ryzhov_andrey.multithreading.task.MultiThreadEx.r;

public class MultiThreadEx {

    public static Runnable r;

    public static void main(String[] args) throws InterruptedException {

        Foo foo = new Foo();
        ExecutorService es = Executors.newFixedThreadPool(3);

        es.execute(new Thread_C(foo));
        es.execute(new Thread_B(foo));
        es.execute(new Thread_A(foo));

        es.shutdown();
    }
}

class Thread_A implements Runnable {
    private Foo foo;

    public Thread_A(Foo foo) {
        this.foo = foo;
    }
    @Override
    public void run() {
        foo.first(r);
    }
}

class Thread_B implements Runnable {
    private Foo foo;

    public Thread_B(Foo foo) {
        this.foo = foo;
    }
    @Override
    public void run() {
        foo.second(r);
    }
}

class Thread_C implements Runnable {
    private Foo foo;

    public Thread_C(Foo foo) {
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
