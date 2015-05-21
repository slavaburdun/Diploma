package ua.kpi.burdun.diploma.optimization.likeflumejava;

import java.util.List;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by burdun on 19.05.2015.
 */
public class ParallelDoFusion {

    private static void problem(double step, double start, double end) {
        System.out.println("Parallel_using_LongStream_and_mapToDouble:");
        final long startTime = System.currentTimeMillis();

        DoubleUnaryOperator funcD1 =
                x -> sin(x) * sin(x) + cos(x) * cos(x);
        DoubleUnaryOperator calcFuncD1 = x -> step * x;
        DoubleUnaryOperator sqFuncDouble1 = funcD1.andThen(calcFuncD1);

        DoubleUnaryOperator funcD2 =
                x -> sin(x) * sin(x) + cos(x) * cos(x);
        DoubleUnaryOperator calcFuncD2 = x -> step * x;
        DoubleUnaryOperator sqFuncDouble2 = funcD2.andThen(calcFuncD2);

        DoubleUnaryOperator funcD3 =
                x -> sin(x) * sin(x) + cos(x) * cos(x);
        DoubleUnaryOperator calcFuncD3 = x -> step * x;
        DoubleUnaryOperator sqFuncDouble3 = funcD3.andThen(calcFuncD3);

        double sum1 = LongStream.
                range(0, (long) ((end - start) / step)).
                parallel().
                mapToDouble(i -> start + step * i).
                map(sqFuncDouble1).
                sum();

        double sum2 = LongStream.
                range(0, (long) ((end - start) / step)).
                parallel().
                mapToDouble(i -> start + step * i).
                map(sqFuncDouble2).
                sum();

        double sum3 = LongStream.
                range(0, (long) ((end - start) / step)).
                parallel().
                mapToDouble(i -> start + step * i).
                map(sqFuncDouble3).
                sum();

        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime));
        System.out.println("sum1 == " + sum1);
        System.out.println("sum2 == " + sum2);
        System.out.println("sum3 == " + sum3);
        System.out.println();
    }


    /*private static void tryUseCommonInput_Double(double step, double start, double end) {
        System.out.println("Parallel_using_LongStream_and_mapToDouble:");
        final long startTime = System.currentTimeMillis();

        DoubleUnaryOperator funcD1 =
                x -> sin(x) * sin(x) + cos(x) * cos(x);
        DoubleUnaryOperator calcFuncD1 = x -> step * x;
        DoubleUnaryOperator sqFuncDouble1 = funcD1.andThen(calcFuncD1);

        DoubleUnaryOperator funcD2 =
                x -> sin(x) * sin(x) + cos(x) * cos(x);
        DoubleUnaryOperator calcFuncD2 = x -> step * x;
        DoubleUnaryOperator sqFuncDouble2 = funcD2.andThen(calcFuncD2);

        DoubleUnaryOperator funcD3 =
                x -> sin(x) * sin(x) + cos(x) * cos(x);
        DoubleUnaryOperator calcFuncD3 = x -> step * x;
        DoubleUnaryOperator sqFuncDouble3 = funcD3.andThen(calcFuncD3);

        List<Double> thing = Stream.
                iterate(0.0, s -> s + step).
                limit((long) ((end - start) / step)).
                collect(Collectors.toList());

        double sum1 = thing.stream().
                parallel().
                map(sqFuncDouble1).
                sum();

        double sum2 = thing.stream().
                parallel().
                mapToDouble(i -> start + step * i).
                map(sqFuncDouble2).
                sum();

        double sum3 = thing.stream().
                parallel().
                mapToDouble(i -> start + step * i).
                map(sqFuncDouble3).
                sum();

        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime));
        System.out.println("sum1 == " + sum1);
        System.out.println("sum2 == " + sum2);
        System.out.println("sum3 == " + sum3);
        System.out.println();
    }*/


    private static void tryUseCommonInput_Long(double step, double start, double end) {
        System.out.println("Parallel_using_LongStream_and_mapToDouble:");
        final long startTime = System.currentTimeMillis();

        DoubleUnaryOperator funcD1 =
                x -> sin(x) * sin(x) + cos(x) * cos(x);
        DoubleUnaryOperator calcFuncD1 = x -> step * x;
        DoubleUnaryOperator sqFuncDouble1 = funcD1.andThen(calcFuncD1);

        DoubleUnaryOperator funcD2 =
                x -> sin(x) * sin(x) + cos(x) * cos(x);
        DoubleUnaryOperator calcFuncD2 = x -> step * x;
        DoubleUnaryOperator sqFuncDouble2 = funcD2.andThen(calcFuncD2);

        DoubleUnaryOperator funcD3 =
                x -> sin(x) * sin(x) + cos(x) * cos(x);
        DoubleUnaryOperator calcFuncD3 = x -> step * x;
        DoubleUnaryOperator sqFuncDouble3 = funcD3.andThen(calcFuncD3);

        List<Double> thing = Stream.
                iterate(0.0, s -> s + step).
                limit((long) ((end - start) / step)).
                collect(Collectors.toList());

/*
        List<Long> thing = LongStream.
                range(0, (long) ((end - start) / step)).
                collect(Collectors.toList());
*/

        double sum1 = thing.stream().
                parallel().
                mapToDouble(i -> start + step * i).
                map(sqFuncDouble1).
                sum();

        double sum2 = thing.stream().
                parallel().
                mapToDouble(i -> start + step * i).
                map(sqFuncDouble2).
                sum();

        double sum3 = thing.stream().
                parallel().
                mapToDouble(i -> start + step * i).
                map(sqFuncDouble3).
                sum();

        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime));
        System.out.println("sum1 == " + sum1);
        System.out.println("sum2 == " + sum2);
        System.out.println("sum3 == " + sum3);
        System.out.println();
    }


    public static void main(String[] args) {

        double step = 0.001;
        double start = 0.0;
        double end = 100_000.0;

        //problem(step, start, end);
        //tryUseCommonInput_Double(step, start, end);
    }
}
