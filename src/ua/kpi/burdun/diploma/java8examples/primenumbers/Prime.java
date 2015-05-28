package ua.kpi.burdun.diploma.java8examples.primenumbers;

import java.util.Arrays;
import java.util.List;

/**
 * Created by burdun on 04.02.2015.
 */
public class Prime {

    public static void main(String[] args) throws Exception {
        final int threshold = 350_000_000;
        System.out.println("threshold == " + threshold);

        System.out.println("Started generating data.");
        //final List<Integer> candidates = IntStream.range(2, threshold).boxed().collect(Collectors.toList());
        Integer[] ints = new Integer[threshold];
        Arrays.fill(ints, /*1_046_527*/ 27_644_437);
        final List<Integer> candidates = Arrays.asList(ints);
        System.out.println("candidates.size() == " + candidates.size());
//        for (int i = 75_000_000-1; i > 74_999_990; i--) {
//            System.out.println(candidates.get(i));
//        }
//        List<Integer> candidates = new ArrayList<>(threshold);
//        for (int i = 0; i < threshold; i++) {
//            candidates.add(i);
//        }
        System.out.println("Finished generating data.");

        System.out.println("Started calculating.");
        final long start = System.currentTimeMillis();
        //List primeNumbers = new ArrayList<>(threshold);
        candidates.parallelStream().filter(Prime::isPrime);//.collect(Collectors.toList());
        System.out.println("Execution time: " + (System.currentTimeMillis() - start));

        //System.out.println("primeNumbers.size() == " + primeNumbers.size());
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
