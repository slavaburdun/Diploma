package ua.kpi.burdun.diploma.java8examples.primenumbers.squares;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by burdun on 05.02.2015.
 */
public class Squares2
{
    private int i = 1;

    public int next()
    {
        int thisOne = i++;
        return thisOne * thisOne;
    }

    public List<Integer> nextN(int n)
    {
        List<Integer> l = new ArrayList<>();

        for (int i = 0; i < n; i++)
        {
            l.add(next());
        }

        return l;
    }

    public static void main(String args[])
    {
        Squares2 squareGenerator = new Squares2();

        squareGenerator.nextN(2).forEach(System.out::println);
        squareGenerator.nextN(2).forEach(System.out::println);
    }
}
