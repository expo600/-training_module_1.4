package main.java.com.ryzhov_andrey.multithreading.chapter_28;

// расширить класс Phaser  и переопределить метод onAdvance()
// таким образом, чтобы было выполнено только определенное кол-во фаз

import java.util.concurrent.Phaser;

class MyPhaser extends Phaser {
    int numPhases;

    MyPhaser(int parties, int phaseCount) {
        super(parties);
        numPhases = phaseCount - 1;
    }
// переопределить метод onAdvance(), чтобы выполнить определенное кол-во фаз

    @Override
    protected boolean onAdvance(int p, int regParties) {
        // следующий оператор println() требуется только для цели иллюстрации.
        // как правило метод onAdvance() не отображает выводимые данные
        System.out.println("Phase " + p + " completed.\n");

        // возвратить логическое значение true если все фазы завершены
        if (p == numPhases || regParties == 0)
            return true;
        // в противном случае возвращать false
        return false;
    }
}


public class PhaserDemo_2 {
    public static void main(String[] args) {
        MyPhaser phsr = new MyPhaser(1, 4);
        System.out.println("Running threads\n");
        new MyThread_6(phsr, "A");
        new MyThread_6(phsr, "B");
        new MyThread_6(phsr, "C");

        // ожидать завершения определенного кол-ва фаз
        while (!phsr.isTerminated()) {
            phsr.arriveAndAwaitAdvance();
        }
        System.out.println("Phase synchronizer completed");
    }
}

// поток исполнения использующий синхронизатор фаз типа Phaser
class MyThread_6 implements Runnable {
    Phaser phsr;
    String name;

    public MyThread_6(Phaser phsr, String name) {
        this.phsr = phsr;
        this.name = name;
        phsr.register();
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (!phsr.isTerminated()) {
            System.out.println("Thread " + name + " starts the first phase " + phsr.getPhase());
            phsr.arriveAndAwaitAdvance();

            // Небольшая пауза, чтобы не нарушить порядок ввода
            // Только для иллюстрации, но не обязательно для правильного функционирования синхронизатора фаз
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                System.out.println(e);
            }

        }
    }
}