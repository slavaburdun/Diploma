package ua.kpi.burdun.diploma.examples.primenumbers.squares;

import java.util.stream.IntStream;

/**
 * Created by burdun on 05.02.2015.
 */
public class Squares3
{
    public static void main(String args[])
    {
        IntStream myStream = IntStream.iterate(1,
                i -> ((int) Math.pow(Math.sqrt(i) + 1, 2)));

        myStream.limit(10).forEach(System.out::println);
        myStream.limit(10).forEach(System.out::println);

    }
}
