package ua.kpi.burdun.diploma.java8examples.tensubtlemistakes;

import java.util.stream.IntStream;

/**
 * Created by burdun on 18.05.2015.
 */
public class TenSubtleMistakes {

    public static void doMistakeOne() {
        IntStream stream = IntStream.of(1, 2);
        stream.forEach(System.out::println);
        // That was fun! Let's do it again!
        stream.forEach(System.out::println);

    }


    public static void main(String[] args) {
        doMistakeOne();
    }
}
