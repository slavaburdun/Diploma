package ua.kpi.burdun.diploma.forkingstream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static ua.kpi.burdun.diploma.fixedbatchspliterator.FixedBatchSpliterator.withBatchSize;

/**
 * Created by burdun on 28.05.2015.
 */
public class StreamForker_WordCount_Benchmark {

    private final Path inputPath;

    public StreamForker_WordCount_Benchmark(String inputPath) {
        this.inputPath = Paths.get(inputPath);
    }


    public void testSequentially() throws IOException {
        System.out.println("testSequentially:");


        long start = System.currentTimeMillis();

        Stream<String> lines = Files.lines(inputPath);
        Map<Object, Long> words = lines
                .map((String line) -> line.split("\\W"))
                .flatMap(Arrays::stream)
                .map(String::toLowerCase)
                .collect(groupingBy(s -> s, counting()));
        lines.close();

        System.out.println("Execution time: " + (System.currentTimeMillis() - start));

        //System.out.println("Words: " + words);
        System.out.println();
    }


    public void testStreamForkerSequentially() throws IOException {
        System.out.println("testSequentially:");


        long start = System.currentTimeMillis();

        Stream<String> lines = Files.lines(inputPath);

        StreamForker.Results results = new StreamForker<String>(lines)
                .fork("WordCount", s -> s.map((String line) -> line.split("\\W"))
                        .flatMap(Arrays::stream)
                        .map(String::toLowerCase)
                        .collect(groupingBy(str -> str, counting())))
                .fork("Number of words", s -> s.map((String line) -> line.split("\\W"))
                        .count())
                .getResults();

        Map<Object, Long> words = results.get("WordCount");
        Long numberOfWords = results.get("Number of words");

        System.out.println("Execution time: " + (System.currentTimeMillis() - start));

        System.out.println("Words: " + words);
        System.out.println("Number of words: " + numberOfWords);
        System.out.println();
    }


    public void testParallel() throws IOException {
        System.out.println("testParallel:");


        long start = System.currentTimeMillis();

        Stream<String> lines = Files.lines(inputPath);
        Map<Object, Long> words = lines.parallel()
                .map(line -> line.split("\\W"))
                .flatMap(Arrays::stream)
                .map(String::toLowerCase)
                .collect(groupingByConcurrent(s -> s, counting()));
        lines.close();

        System.out.println("Execution time: "
                + (System.currentTimeMillis() - start));

        //System.out.println("Words: " + words);
        System.out.println();
    }


    public void testWithFixedBatchSpliterator() throws IOException {
        System.out.println("testWithFixedBatchSpliterator:");

        long start = System.currentTimeMillis();

        Stream<String> lines = withBatchSize(Files.lines(inputPath), 10);

        Map<Object, Long> words = lines.parallel()
                .map(line -> line.split("\\W"))
                .flatMap(Arrays::stream)
                .map(String::toLowerCase)
                .collect(groupingByConcurrent(s -> s, counting()));
        lines.close();

        System.out.println("Execution time: "
                + (System.currentTimeMillis() - start));

        //System.out.println("Words: " + words);
        System.out.println();
    }


    public static void main(String[] args) throws IOException {
        StreamForker_WordCount_Benchmark wordCountBenchmark = new StreamForker_WordCount_Benchmark("big.txt");
        wordCountBenchmark.testSequentially();
        wordCountBenchmark.testStreamForkerSequentially();
        //wordCountBenchmark.testWithFixedBatchSpliterator();
    }
}
