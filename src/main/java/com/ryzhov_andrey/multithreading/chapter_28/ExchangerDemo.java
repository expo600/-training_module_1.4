package main.java.com.ryzhov_andrey.multithreading.chapter_28;

import java.util.concurrent.Exchanger;

public class ExchangerDemo {
    public static void main(String[] args) {
        Exchanger<String> exgr = new Exchanger<>();
        new UseString(exgr);
        new MakeString(exgr);
    }
}

// поток типа Thread, формирующий символьную строку
class MakeString implements Runnable {
    Exchanger<String> ex;
    String str;

    MakeString(Exchanger<String> с) {
       ex = с;
        str = new String();
        new Thread(this).start();
    }

    public void run() {
        char ch = 'A';
        for (int i = 0; i < 4; i++) {
            //заполнить буфер
            for (int j = 0; j < 7; j++)
                str += (char) ch++;
                // обменять заполнениый буфер на пустой
                try {
                    str = ex.exchange(str);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        }
    }


// поток типа Thread, использующий символьную строку
class UseString implements Runnable {
    Exchanger<String> ex;
    String str;

    UseString(Exchanger<String> с) {
        ex = с;
        new Thread(this).start();
    }

    public void run() {
        for (int i = 0; i < 4; i++) {
            try {
                // обменять пустой буфер на полный
                str = ex.exchange(new String());
                System.out.println("Received :" + str);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}