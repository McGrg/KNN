package gregory;

import gregory.Calculating.Entity;
import gregory.Calculating.FutureThread;
import gregory.Calculating.Result;
import gregory.reading.Reader;
import gregory.reading.StrToInt;
import gregory.reading.Writer;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) {
        try {
            int k=0;
            int numberofThreads = 0; //количество создаваемых потоков
            k= StrToInt.stringToInt(args[0], 1);
            numberofThreads = StrToInt.stringToInt(args[1], 1);
            int numberofTestValues = 60000; //количество элементов, по которым производится проверка
            int numberofValuesToCheck = 10000; //количество распознаваемых элементов
            double precision = 0;
            int currentValue = 0;
            int percent;
            long time; // переменная для подсчета времени исполнения алгоритма
            String data; // переменная для передачи в log файл

            // pathname 1 "C:\\Users\\MSI GT70\\Desktop\\Studies\\Java\\KNN\\t10k-images.idx3-ubyte" файл с картинками в байтовый массив
            // pathname 1 "C:\\Users\\MSI GT70\\Desktop\\Studies\\Java\\KNN\\t10k-labels.idx1-ubyte" файл с описанием картинок в байтовый массив

            // pathname 2 "F:\\Java\\KNN\\train-images.idx3-ubyte", "F:\\Java\\KNN\\train-labels.idx1-ubyte"
            // pathname 2 "F:\\Java\\KNN\\t10k-images.idx3-ubyte",  "F:\\Java\\KNN\\t10k-labels.idx1-ubyte"
            /**
             * Создание коллекций проверочных элементов Entity и распозноваемых элементов Entity
             * из соответствующих файлов.
             */
            List<Entity> trainingList = Reader.fileReader("D:\\Java\\KNN 16.01.2017\\train-images.idx3-ubyte", "D:\\Java\\KNN 16.01.2017\\train-labels.idx1-ubyte", numberofTestValues);
            List<Entity> toCheckList = Reader.fileReader("D:\\Java\\KNN 16.01.2017\\t10k-images.idx3-ubyte", "D:\\Java\\KNN 16.01.2017\\t10k-labels.idx1-ubyte", numberofValuesToCheck);
            time = System.currentTimeMillis();

//            Result res = new Result();

//            printList(trainingList);
//            printList(toCheckList);


            /**
             * Основная логика. Вызов статического метода fileCompare класса Analizator. Метод
             * возвращает экземпляр класса Result, который содержит 2 поля: определенной значение
             * картинки и истинное значение картинки. Далее подсчитывается точность распознования
             * (переменная precision).
             */
//            for (Entity e : toCheckList) {
//                //                System.out.println("Расстояния ");
//                res = Analizator.fileCompare(trainingList, e);
//                //
//                if ((res.getCalculatedResult() - res.getLabelResult()) != 0) {
//                    e.printEntity();
//                    System.out.println(res.getCalculatedResult() + " значение из файла" + res.getLabelResult());
//                    precision++;
//                }
//            }

            /**
             * Основная логика. Создание экземпляра ExecutorService, и коллекции элементов Future,
             * для передачи им результатов выполнения потока FutureThread, который в свою очередь
             * вызывает статический метод fileCompare класса Analizator. В конструктор потока FutureThread,
             * как исходные данные передается итератор коллекции toCheckList. Метод Analizator
             * возвращает экземпляр класса Result, который содержит 2 поля: определенной значение
             * картинки и истинное значение картинки. экземпляры Result собираются в коллекцию futureList
             * Далее подсчитывается точность распознования (переменная precision).
             */
            ExecutorService executor = Executors.newFixedThreadPool(numberofThreads);
            List<Future<Result>> futureList = new LinkedList<>();
            ListIterator<Entity> toCheckListIter = toCheckList.listIterator();
            while (toCheckListIter.hasNext()){
                currentValue++;//счетчик для процентов
                FutureThread futureThread = new FutureThread(trainingList, toCheckListIter.next(), k, numberofValuesToCheck, currentValue);
                Future<Result> future = executor.submit(futureThread);
                futureList.add(future);
            }
            executor.shutdown();

            for (Future<Result> fut:futureList){
                if ((fut.get().getCalculatedResult() - fut.get().getLabelResult()) != 0) {
                    precision++;
                }
            }

            time = System.currentTimeMillis() - time;
            System.out.print("\r");
            System.out.println("Выполнено: 100%");
            System.out.println("Количество ошибок: " + precision);
            precision = (100 - ((precision * 100) / numberofValuesToCheck));// подсчет точности распознования
            data = time + ";  precision is: " + precision + "; number of units to check: " + numberofValuesToCheck + //строка для записи в log
                    "; number of test units: " + numberofTestValues + "; number of threads" + numberofThreads + "; nearest neighbors" + k;
            Writer.logWriter(data); // метод для записи данных в  log.txt файл
            System.out.println("Точность распознования: " + precision);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Вспомогательный метод для вывода изображения из коллекции на консоль
     */


    public static void printList(List<Entity> list) {
        for (Entity e : list) {
            System.out.println(e.getLabel());
            for (int i = 0; i < 784; i = i + 28) {
                for (int k = 0; k < 28; k++) {  // вывод на печать матрицы 28х28
                    if (e.getValue(i + k) != 0) {
                        System.out.print("x");
                    } else System.out.print("-");
                }
                System.out.println("");
            }
        }
    }
}