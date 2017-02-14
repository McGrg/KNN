package gregory.Calculating;

import java.util.*;
import java.lang.Math;

/** Класс Analizator для распознования изображений методом KNN
 *
 */
public class Analizator {

    private int checkedValue;

    /** метод fileCompare получает на вход эталонную коллекцию и проверяемый элемент. Каждый элемент коллекции
     * содержит вектор int-значений, описывающих изображение и bite- значение этого изображения.
     * На выходе получаем элемент класса Result c 2-мя полями: определенное значение и истинное значение
     * из проверяемого элемента.
     */

    public static Result fileCompare(List<Entity> sourceList, Entity checkList, int k) {
        Result res = new Result();

        List<Distances> distanceList = new LinkedList<>();//Коллекция "Евклидовых расстояний" от проверяемого элемента до каждого элемента эталонной коллекции
        for (Entity source : sourceList) {// подсчет "Евклидовых расстояний" до каждого элемента эталонной коллекции
            double dist = 0;
            for (int i = 0; i < 784; i++) {
                dist += Math.pow((source.getValue(i) - checkList.getValue(i)), 2);
            }
            Distances distClass = new Distances();// элемент класса Distances в котором 2 поля: "Евклидово расстояние" и значение элемента из эталонной коллекции до которого это расстояние подсчитано
            distClass.setDist(Math.sqrt(dist));
            distClass.setLabel(source.getLabel());
            distanceList.add(distClass);
        }
        res.setCalculatedResult(checkedValue(sortDistances(distanceList), k));// определенное значение
        res.setLabelResult(checkList.getLabel());//
        return res;
    }

    /** метод sortDistances сортирует по возрастанию постыпающую на вход коллекцию элементов класса Distances.
     * сортировка осуществляется по полю расстояние: dist.
     */

    private static List<Distances> sortDistances(List<Distances> listOfDistances) {
        Collections.sort(listOfDistances, new Comparator<Distances>() {
            @Override
            public int compare(Distances o1, Distances o2) {
                return o1.getDist().compareTo(o2.getDist());
            }
        });
        return listOfDistances;
    }

    /** метод checkedValue получает на вход отсортированную по возрастанию коллекцию элементов
     * Distances и из "k" первых элементов создает коллекцию HashMap, где ключем каждого элемента выступает поле
     * элемента Distances, описывающее значение изображения из эталонной коллекции,
     * во втором поле - количество раз, сколько это значение появилось в "k" первых эле-тах
     * коллекции элементов Distances. Далее коллекция HashMap сортируется по убыванию по полю "значений"
     * и выбирается 1-й элемент коллекции в поле ключа которого значение наиболее часто встречающееся
     * в первых "k" эл-тых коллекции.
     */

    private static int checkedValue(List<Distances> listOfDistances, int k) {
        Map<Integer, Integer> value = new HashMap<>();
        ListIterator<Distances> iter = listOfDistances.listIterator();// создание итератора для прохождения по коллекции
        for (int i = 0; i < k; i++) { //копирование "k" первых элементов
            Distances item = iter.next();
            Integer counter = value.get(item.getLabel());
            value.put(item.getLabel(), counter == null ? 1 : counter + 1); //заполнение HashMap
        }
        ArrayList<Map.Entry<Integer, Integer>> valueList = new ArrayList<>(value.entrySet());
        Collections.sort(valueList, new Comparator<Map.Entry<Integer, Integer>>()  {//сортировка по убыванию по полю "значений"
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return -o1.getValue().compareTo(o2.getValue());
            }
        });
//        HashMap.Entry<Integer, Integer> firstEntry = // вывод первого эл-та коллекции
//                (Map.Entry<Integer, Integer>) value.entrySet().iterator().next();
        return valueList.get(0).getKey();
    }
    }


