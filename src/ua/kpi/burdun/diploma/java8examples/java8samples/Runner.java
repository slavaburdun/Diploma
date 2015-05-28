package ua.kpi.burdun.diploma.java8examples.java8samples;

/**
 * Created by burdun on 20.01.2015.
 */
public class Runner {

    public static void main(String[] args) {

        /*{
            String[] strings = new String[]{"daughter", "mama", "grandma", "papa"};

            //System.out.println(SortingUsingLambda.sortWithComparator(strings));
            System.out.println(SortingUsingLambda.sortWithLambda(strings));
        }*/

        {
            SortingUsingStreams.sortUsingParallelStream();
            //SortingUsingStreams.sortUsingStream();
        }
    }
}
