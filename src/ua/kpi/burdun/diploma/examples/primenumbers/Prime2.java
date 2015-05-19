package ua.kpi.burdun.diploma.examples.primenumbers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by burdun on 05.02.2015.
 */
public class Prime2 {
    public static void main(String[] args) throws Exception {
        final int threshold = 500_000_000;
        //Integer[] ints = new Integer[threshold];
        System.out.println("threshold == " + threshold);

        //IntStream candidates = IntStream.range(2, threshold);

        System.out.println("Started calculating.");
        final long start = System.currentTimeMillis();
        final List primeNumbers = IntStream.range(2, threshold)
                                     .parallel()
                                     .filter(Prime2::isPrime)
                                     .mapToObj(i -> Integer.valueOf(i))
                                     .collect(Collectors.toList());
        System.out.println("Execution time: " + (System.currentTimeMillis() - start));

        System.out.println("primeNumbers.size() == " + primeNumbers.size());
        //System.out.println(primeNumbers.get(0));
    }

    private static boolean isPrime(final int candidate) {
        for (int i = 2; i * i <= candidate; ++i) {
            if (candidate % i == 0) {
                return false;
            }
        }
        return true;
    }
}
