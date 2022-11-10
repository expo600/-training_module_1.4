package main.java.com.ryzhov_andrey.multithreading.chapter_28;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

// класс ForkJoinTask преобразует (через класс RecursiveAction) элементы массива типа double
class Transform extends RecursiveAction {

    int seqThreshold;  // порог последовательного выполнения, устанавлеваемый конструктором

    double data[];     // обрабатываемый массив

    int start, end;   // определить часть обрабатываемых данных

    public Transform(double[] data, int start, int end, int seqThreshold) {
        this.seqThreshold = seqThreshold;
        this.data = data;
        this.start = start;
        this.end = end;
    }

    // этот метод выполняет параллельное вычисление
    @Override
    protected void compute() {
        // если кол-во элементов меньше порогового значения, выполнять далбнейшую обработку последовательно
        if ((end - start) < seqThreshold) {
            // в следующем фрагменте кода элементу по четному индексу присваивается квадратный корень его
            // первоначального значения, а элементу по нечетному индексу - кубический корень
            // это код предназначен только для потребления времени ЦП, чтобы сделать нагляднее
            // эффект от параллельного выполнения
            for (int i = start; i < end; i++) {
                if (data[i] % 2 == 0)
                    data[i] = Math.sqrt(data[i]);
                else
                    data[i] = Math.cbrt(data[i]);
            }
        } else {
            // в противном случае разделять данные на меньшие части

            // найти середину
            int middle = (start + end) / 2;

            // запустить новые подзадачи на выполнение, используя разделенные на части данные
            invokeAll(new Transform(data, start, middle,seqThreshold), new Transform(data, middle, end,seqThreshold));
        }
    }
}

// продемонстрировать параллельное выполнение
public class ForkJoinTaskExp {
    public static void main(String[] args) {
        int pLevel;
        int threshold;

        if (args.length != 2) {
            System.out.println("Usage: ForkJoinTaskExp concurrency threshold ");
            return;
        }
        pLevel = Integer.parseInt(args[0]);
        threshold = Integer.parseInt(args[1]);

        // эти переменные используются для измерения времени выполнения задачи
        Long beginT, endT;

        // создать пул задач. Обратить внимание на установку уровня параллелизма
        ForkJoinPool fjp = new ForkJoinPool(pLevel);

        double nums[] = new double[1000000];

        for (int i = 0; i < nums.length; i++) {
            nums[i] = (double) i;

            Transform task = new Transform(nums, 0, nums.length, threshold);

            // начать измерение времени выполнения задачи
            beginT = System.nanoTime();

            // запустить главную задачу типа ForkJoinTask на выполнение
            fjp.invoke(task);

            // завершить измерение времени исполнения задачи
            endT = System.nanoTime();

            System.out.println("Concurrency level: "+pLevel);
            System.out.println("Sequential processing threshold: "+threshold);
            System.out.println("Elapsed time: "+(endT-beginT)+" nanosecond");
            System.out.println();
        }
    }
}
