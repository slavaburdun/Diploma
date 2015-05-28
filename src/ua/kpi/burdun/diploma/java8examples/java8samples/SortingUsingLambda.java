package ua.kpi.burdun.diploma.java8examples.java8samples;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by burdun on 20.01.2015.
 */
public class SortingUsingLambda {

    public static List<String> sortWithComparator(String[] strings) {
        Arrays.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String firstStr, String secondStr) {
                return Integer.compare(firstStr.length(), secondStr.length());
            }
        });
        return Arrays.asList(strings);
    }

    public static List<String> sortWithLambda(String[] strings) {
        Arrays.sort(strings, (firstStr, secondStr) -> Integer.compare(firstStr.length(), secondStr.length()));
        return Arrays.asList(strings);
    }
}
