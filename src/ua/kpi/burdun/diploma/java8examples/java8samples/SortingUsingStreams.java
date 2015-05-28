package ua.kpi.burdun.diploma.java8examples.java8samples;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by burdun on 03.02.2015.
 */
public class SortingUsingStreams {

    static private List<String> values;

    static {
        int max = 100000000;
        values = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }

    }

    public static void sortUsingStream() {

        long t0 = System.nanoTime();

        long count = values.stream().sorted().count();
        //System.out.println(count);

        long t1 = System.nanoTime();

        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("sequential sort took: %d ms", millis));
    }


    public static void sortUsingParallelStream() {

        long t0 = System.nanoTime();

        long count = values.parallelStream().sorted().count();
        //System.out.println(count);

        long t1 = System.nanoTime();

        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("parallel sort took: %d ms", millis));
    }
}
