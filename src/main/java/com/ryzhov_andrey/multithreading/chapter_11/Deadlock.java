package main.java.com.ryzhov_andrey.multithreading.chapter_11;

// пример взаимной блокировки
class A {
    synchronized void foo(B b) {
        String name = Thread.currentThread().getName();
        System.out.println(name + " вошел в метод A.foo()");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Класс A прерван");
        }
        System.out.println(name + " пытаться вызвать метод B.last()");
        b.last();
    }

    synchronized void last() {
        System.out.println("В методе A.last()");
    }
}

class B {
    synchronized void bar(A a) {
        String name = Thread.currentThread().getName();
        System.out.println(name + " вошел в метод B.bar()");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Класс B прерван");
        }
        System.out.println(name + " пытаться вызвать метод A.last()");
        a.last();
    }

    synchronized void last() {
        System.out.println("В методе A.last()");
    }
}


public class Deadlock implements Runnable {
    A a = new A();
    B b = new B();

    Deadlock() {
        Thread.currentThread().setName("Главный поток");
        Thread thread = new Thread(this, "Соперничающий поток");
        thread.start();

        a.foo(b); // получить блокировку для объекта a в этом потоке исполнения
        System.out.println("Назад в главный поток");
    }

    public void run() {
        b.bar(a); // получить блокировку для объекта b в другом потоке исполнения
        System.out.println("Назад в другой поток");
    }

    public static void main(String[] args) {
        new Deadlock();
    }
}
