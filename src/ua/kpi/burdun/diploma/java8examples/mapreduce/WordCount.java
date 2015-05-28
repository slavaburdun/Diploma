package ua.kpi.burdun.diploma.java8examples.mapreduce;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.groupingByConcurrent;

/**
 * Created by burdun on 28.05.2015.
 */
public class WordCount {

    public static void testSequentially() throws IOException {
        System.out.println("testSequentially:");

        Stream<String> lines = Files.lines(Paths.get("pg42671.txt"));
        long start = System.currentTimeMillis();

        Map<Object, Long> words = lines
                .map( (String line) -> line.split("\\W") )
                .flatMap(Arrays::stream)
                .map(String::toLowerCase)
                .collect(groupingBy(s -> s, counting()));

        System.out.println("Execution time: " + (System.currentTimeMillis() - start));
        lines.close();
        System.out.println("Words: " + words);
        System.out.println();
    }


    public static void testParallel() throws IOException {
        System.out.println("testParallel:");

        Stream<String> lines = Files.lines(Paths.get("pg42671.txt"));
        long start = System.currentTimeMillis();

        Map<Object, Long> words = lines.parallel()
                .map(line -> line.split("\\W"))
                .flatMap(Arrays::stream)
                .map(String::toLowerCase)
                .collect(groupingByConcurrent(s -> s, counting()));

        System.out.println("Execution time: "
                + (System.currentTimeMillis() - start));
        lines.close();
        System.out.println("Words: " + words);
    }


    public static void main(String[] args) throws IOException {
        testSequentially();
        testParallel();
    }

}
