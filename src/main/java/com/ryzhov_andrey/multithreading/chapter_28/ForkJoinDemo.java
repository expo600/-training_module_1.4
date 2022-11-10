package main.java.com.ryzhov_andrey.multithreading.chapter_28;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

// класс ForkJoinTask преобразует (через класс RecursiveAction) значения элементов массива в их квадратные корни
class SqrtTransform extends RecursiveAction {
    // пороговое значение, установить экспериментально
    final int seqThreshold = 1000;

    // обрабатываемый массив
    double[] data;

    // определить часть обрабатываемых данных
    int start, end;

    public SqrtTransform(double[] vals, int s, int e) {
        this.data = vals;
        this.start = s;
        this.end = e;
    }

    // этот метод выполняет параллельное вычисление
    @Override
    protected void compute() {
        // если кол-во элементов меньше порогового значения, выполнять далбнейшую обработку последовательно
        if ((end - start) < seqThreshold) {
            // преобразовывать каждый элемент массива в его квадратный корень
            for (int i = start; i < end; i++) {
                data[i] = Math.sqrt(data[i]);
            }
        } else {
            // в противном случае разделять данные на меньшие части

            // найти середину
            int middle = (start + end) / 2;

            // запустить новые подзадачи на выполнение, используя разделенные на части данные
            invokeAll(new SqrtTransform(data, start, middle), new SqrtTransform(data, middle, end));
        }
    }
}

// продемонстрировать параллельное выполнение
public class ForkJoinDemo {
    public static void main(String[] args) {
        // создать пул задач
        ForkJoinPool fjp = ForkJoinPool.commonPool();
        double nums[] = new double[100000];

        // присвоить некоторые значения
        for (int i = 0; i < nums.length; i++)
            nums[i] = (double) i;

        System.out.println("Part of the original sequence: ");

        for (int i = 0; i < 10; i++)
            System.out.print(nums[i] + "  ");
        System.out.println("\n");

        SqrtTransform task = new SqrtTransform(nums, 0, nums.length);

        // запустить главную задачу типа ForkJoinTask на выполнение
        fjp.invoke(task);
        System.out.println("Part of the transformed sequence: ");

        for (int i = 0; i < 10; i++) {
            System.out.format("%.4f", nums[i]);
            System.out.print("  ");
        }

    }

}

