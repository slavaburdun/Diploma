package ua.kpi.burdun.diploma.tailcalloptimization;

import java.math.BigInteger;

/**
 * Created by burdun on 02.06.2015.
 */
public class FactorialBigInteger {

    public static BigInteger factorialRec(final BigInteger number) {
        if (number.equals(BigInteger.ONE))
            return number;
        else
            return number.multiply(factorialRec(number.subtract(BigInteger.ONE)));
    }

    public static BigInteger factorialRec_TailCall(final BigInteger factorial, final BigInteger number) {
        if (number.equals(BigInteger.ONE))
            return factorial;
        else
            return factorialRec_TailCall(factorial.multiply(number), number.subtract(BigInteger.ONE));
    }


    public static void main(String[] args) {
//        System.out.println(factorialRec(new BigInteger("16000")));
        System.out.println(factorialRec_TailCall(BigInteger.ONE, new BigInteger("16000")));
    }
}
