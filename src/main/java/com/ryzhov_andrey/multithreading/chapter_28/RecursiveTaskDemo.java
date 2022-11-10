package main.java.com.ryzhov_andrey.multithreading.chapter_28;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

// класс RecursiveTask, используемый для вычисления суммы элементов значений в массиве типа double
class Sum_1 extends RecursiveTask<Double> {

    final int seqThreshold = 500;  // порог последовательного выполнения

    double data[];     // обрабатываемый массив

    int start, end;   // определить часть обрабатываемых данных

    public Sum_1(double[] data, int start, int end) {
        this.data = data;
        this.start = start;
        this.end = end;
    }

    // определить сумму элементов в массиве типа double
    @Override
    protected Double compute() {
        double sum = 0;
        // если кол-во элементов меньше порогового значения, выполнять далбнейшую обработку последовательно
        if ((end - start) < seqThreshold) {
            // суммировать элементы массива
            for (int i = start; i < end; i++)
                sum += data[i];
        } else {
            // в противном случае разделять данные на меньшие части

            // найти середину
            int middle = (start + end) / 2;

            // запустить новые задачи на выполнение, используя разделенные на части данные
            Sum_1 subTaskA = new Sum_1(data, start, middle);
            Sum_1 subTaskB = new Sum_1(data, middle, end);

            // запустить каждую задачу путем разветвления
            subTaskA.fork();
            subTaskB.fork();

            //ожидать завершения задачи и накопить результаты
            sum = subTaskA.join() + subTaskB.join();
        }
        return sum;
    }
}

// продемонстрировать параллельное выполнение
public class RecursiveTaskDemo {
    public static void main(String[] args) {

        // создать пулл задач
        ForkJoinPool fjp = new ForkJoinPool();

        double[] nums = new double[5000];

        // инициализировать массив nums, чередующимися положительными и отрицательными значениями
        for (int i = 0; i < nums.length; i++)
            nums[i] = (double) ((i % 2 == 0) ? i : -i);
        Sum_1 task = new Sum_1(nums, 0, nums.length);

        // запустить задачи типа ForkJoinTask на выполнение, метод invoke() возвращает результат
        double summation = fjp.invoke(task);
        System.out.println("Summation: " + summation);
    }
}
