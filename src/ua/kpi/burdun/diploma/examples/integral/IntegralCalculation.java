package ua.kpi.burdun.diploma.examples.integral;

import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by burdun on 18.05.2015.
 */
public class IntegralCalculation {

    public static double calculateIntegral_Sequentially_using_Stream_and_limit(double step, double start, double end) {
        System.out.println("Sequentially_using_Stream_and_limit:");
        final long startTime = System.currentTimeMillis();

        Function<Double, Double> func =
                x -> sin(x) * sin(x) + cos(x) * cos(x);
        Function<Double, Double> calcFunc = x -> step * x;
        Function<Double, Double> sqFunc = func.andThen(calcFunc);

        double sum = Stream.
                iterate(0.0, s -> s + step).
                limit((long) ((end - start) / step)).
                map(sqFunc).
                reduce(0.0, Double::sum);

        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime));
        System.out.println("sum == " + sum);
        System.out.println();
        return sum;
    }


    public static double calculateIntegral_Sequentially_using_DoubleStream_and_limit(double step, double start, double end) {
        System.out.println("Sequentially_using_DoubleStream_and_limit:");
        final long startTime = System.currentTimeMillis();

        DoubleUnaryOperator funcD =
                x -> sin(x) * sin(x) + cos(x) * cos(x);
        DoubleUnaryOperator calcFuncD = x -> step * x;
        DoubleUnaryOperator sqFuncDouble = funcD.andThen(calcFuncD);

        double sum = DoubleStream.
                iterate(0.0, s -> s + step).
                limit((long) ((end - start) / step)).
                map(sqFuncDouble).
                sum();

        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime));
        System.out.println("sum == " + sum);
        System.out.println();
        return sum;
    }


    public static double calculateIntegral_Parallel_using_DoubleStream_and_limit(double step, double start, double end) {
        System.out.println("Parallel_using_DoubleStream_and_limit:");
        final long startTime = System.currentTimeMillis();

        DoubleUnaryOperator funcD =
                x -> sin(x) * sin(x) + cos(x) * cos(x);
        DoubleUnaryOperator calcFuncD = x -> step * x;
        DoubleUnaryOperator sqFuncDouble = funcD.andThen(calcFuncD);

        double sum = DoubleStream.
                iterate(0.0, s -> s + step).
                limit((long) ((end - start) / step)).
                parallel().
                map(sqFuncDouble).
                sum();

        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime));
        System.out.println("sum == " + sum);
        System.out.println();
        return sum;
    }


    public static double calculateIntegral_Parallel_using_DoubleStream_and_flatMap(double step, double start, double end) {
        System.out.println("Parallel_using_DoubleStream_and_flatMap:");
        final long startTime = System.currentTimeMillis();

        DoubleUnaryOperator funcD =
                x -> sin(x) * sin(x) + cos(x) * cos(x);
        DoubleUnaryOperator calcFuncD = x -> step * x;
        DoubleUnaryOperator sqFuncDouble = funcD.andThen(calcFuncD);

        double chunks = (end - start) / step;
        double sum = DoubleStream.
                iterate(0.0, s -> s + (end - start) / chunks).
                limit((long) chunks).
                parallel().
                flatMap(
                        c -> DoubleStream.
                                iterate(c, s -> s + step).
                                limit((long) ((end - start) / (chunks * step)))).
                map(sqFuncDouble).
                sum();

        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime));
        System.out.println("sum == " + sum);
        System.out.println();
        return sum;
    }


    public static double calculateIntegral_Parallel_using_LongStream_and_mapToDouble(double step, double start, double end) {
        System.out.println("Parallel_using_LongStream_and_mapToDouble:");
        final long startTime = System.currentTimeMillis();

        DoubleUnaryOperator funcD =
                x -> sin(x) * sin(x) + cos(x) * cos(x);
        DoubleUnaryOperator calcFuncD = x -> step * x;
        DoubleUnaryOperator sqFuncDouble = funcD.andThen(calcFuncD);

        double sum = LongStream.
                range(0, (long) ((end - start) / step)).
                parallel().
                mapToDouble(i -> start + step * i).
                map(sqFuncDouble).
                sum();

        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime));
        System.out.println("sum == " + sum);
        System.out.println();
        return sum;
    }



    public static void main(String[] args) {

        double step = 0.001;
        double start = 0.0;
        double end = 100_000.0;

        //calculateIntegral_Sequentially_using_Stream_and_limit(step, start, end);
        calculateIntegral_Sequentially_using_DoubleStream_and_limit(step, start, end);

        //calculateIntegral_Parallel_using_DoubleStream_and_limit(step, start, end);
        //calculateIntegral_Parallel_using_DoubleStream_and_flatMap(step, start, end);

        calculateIntegral_Parallel_using_LongStream_and_mapToDouble(step, start, end);
    }
}
