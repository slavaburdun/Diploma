package ua.kpi.burdun.diploma.java8examples.java8samples;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by burdun on 02.02.2015.
 */
public class Main {

    public static void evaluate(List<Integer> list, Predicate<Integer> predicate) {
        for (Integer n : list) {
            if (predicate.test(n)) {
                System.out.print(n + " ");
            }
        }
        System.out.println();
    }


    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(1,2,3,4,5,6);

        System.out.print("Выводит все четные числа: ");
        evaluate(list, n -> n%2 == 0);

        System.out.print("Квадраты чисел: ");
        list.stream().map(n -> n*n).forEach(n -> System.out.print(n + " "));
        System.out.println();

        System.out.print("Сумма квадратов чисел: ");
        System.out.println(list.stream().map(n -> n * n).reduce((x, y) -> x + y).get());
    }
}
