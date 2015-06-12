package ua.kpi.burdun.diploma.forkingstream.benchmark;

import ua.kpi.burdun.diploma.forkingstream.StreamForker;

import static java.util.concurrent.TimeUnit.SECONDS;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

/**
 * Created by burdun on 09.06.2015.
 */
public class StreamForkerBenchmark {

    private final Path inputPath;
    static double sink1;
    static double sink2;
    static double sink3;
    static long sink11;
    static long sink22;
    static long sink33;


    public StreamForkerBenchmark(String inputPath) throws IOException  {
        this.inputPath = createInput();
    }


    public void measureProcessing_sequentially() throws IOException {
        System.out.println("measureProcessing_sequentially:");

        final long start = System.nanoTime();

        long totalTime = 0;
        for (int i = 0; i < 2; i++) {
            System.out.println("i == " + i);
            Stream<String> lines = Files.lines(inputPath);
            final long time = lines
                    .mapToLong(StreamForkerBenchmark::processLine).sum();
            totalTime += time;
        }

        final double cpuTime = totalTime, realTime = System.nanoTime() - start;
        final int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("          Cores: " + cores);
        System.out.format("       CPU time: %.2f s\n", cpuTime / SECONDS.toNanos(1));
        System.out.format("      Real time: %.2f s\n", realTime / SECONDS.toNanos(1));
        System.out.format("CPU utilization: %.2f%%\n", 100.0 * cpuTime / realTime / cores);

        System.out.println("sink11 == " + sink11);
        System.out.println();
    }

    public void measureProcessing_parallel() throws IOException {
        System.out.println("measureProcessing_parallel:");

        final long start = System.nanoTime();

        long totalTime = 0;
        for (int i = 0; i < 2; i++) {
            System.out.println("i == " + i);
            Stream<String> lines = Files.lines(inputPath);
            final long time = lines
                    .mapToLong(StreamForkerBenchmark::processLine2).parallel().sum();
            totalTime += time;
        }

        final double cpuTime = totalTime, realTime = System.nanoTime() - start;
        final int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("          Cores: " + cores);
        System.out.format("       CPU time: %.2f s\n", cpuTime / SECONDS.toNanos(1));
        System.out.format("      Real time: %.2f s\n", realTime / SECONDS.toNanos(1));
        System.out.format("CPU utilization: %.2f%%\n", 100.0 * cpuTime / realTime / cores);

        System.out.println("sink22 == " + sink22);
        System.out.println();
    }


    public void measureProcessing_StreamForker() throws IOException {
        System.out.println("measureProcessing_StreamForker:");

        final long start = System.nanoTime();

        long totalTime = 0;
        Stream<String> lines = Files.lines(inputPath).parallel();

        StreamForker.Results results = new StreamForker<String>(lines.map(StreamForkerBenchmark::processLine3))
                .fork("time1", s -> s.mapToLong(Long::parseLong).sum())
                .fork("time2", s -> s.mapToLong(Long::parseLong).sum())
                /*.fork("time1", s -> s.mapToLong(Long::parseLong).sum())
                .fork("time2", s -> s.mapToLong(Long::parseLong).sum())*/
                .getResults();

        long time1 = results.get("time1");
        long time2 = results.get("time2");
        /*long time3 = results.get("time3");
        long time4 = results.get("time4");*/
        /*System.out.println("time1 == " + time1);
        System.out.println("time2 == " + time2);*/
        totalTime += time1;
        totalTime += time2;
        /*totalTime += time3;
        totalTime += time4;*/

        final double cpuTime = totalTime, realTime = System.nanoTime() - start;
        final int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("          Cores: " + cores);
        System.out.format("       CPU time: %.2f s\n", cpuTime / SECONDS.toNanos(1));
        System.out.format("      Real time: %.2f s\n", realTime / SECONDS.toNanos(1));
        System.out.format("CPU utilization: %.2f%%\n", 100.0 * cpuTime / realTime / cores);

        System.out.println("sink33 == " + sink33);
        System.out.println();
    }


    private static long processLine(String line) {
        final long localStart = System.nanoTime();
        double d = 0;
        for (int i = 0; i < line.length(); i++)
            for (int j = 0; j < line.length(); j++)
                d += Math.pow(line.charAt(i), line.charAt(j)/32.0);
        /*sink1 += d;
        return System.nanoTime()-localStart;*/
        sink11 += System.nanoTime()-localStart;
        return System.nanoTime()-localStart;
    }

    private static long processLine2(String line) {
        final long localStart = System.nanoTime();
        double d = 0;
        for (int i = 0; i < line.length(); i++)
            for (int j = 0; j < line.length(); j++)
                d += Math.pow(line.charAt(i), line.charAt(j)/32.0);
        /*sink2 += d;
        return System.nanoTime()-localStart;*/
        sink22 += System.nanoTime()-localStart;
        return System.nanoTime()-localStart;
    }

    private static String processLine3(String line) {
        final long localStart = System.nanoTime();
        double d = 0;
        for (int i = 0; i < line.length(); i++)
            for (int j = 0; j < line.length(); j++)
                d += Math.pow(line.charAt(i), line.charAt(j)/32.0);
        /*sink3 += d;
        return String.valueOf(System.nanoTime()-localStart);*/
        sink33 += System.nanoTime()-localStart;
        return String.valueOf(System.nanoTime()-localStart);
    }


    private static Path createInput() throws IOException {
        final Path inputPath = Paths.get("input.txt");
        try (PrintWriter w = new PrintWriter(Files.newBufferedWriter(inputPath))) {
            for (int i = 0; i < 6_000; i++) {
                final String text = String.valueOf(System.nanoTime());
                for (int j = 0; j < 15; j++) w.print(text);
                w.println();
            }
        }
        return inputPath;
    }



    public static void main(String[] args) throws IOException  {
        StreamForkerBenchmark streamForkerBenchmark = new StreamForkerBenchmark("big.txt");
        streamForkerBenchmark.measureProcessing_sequentially();
        streamForkerBenchmark.measureProcessing_parallel();
        streamForkerBenchmark.measureProcessing_StreamForker();
    }
}
