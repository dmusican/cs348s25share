import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Printing {
    public static int total = 0;

    public static void main(String[] args) {
        ArrayList<Integer> myList = new ArrayList<>();
        int size = 1000;
        for (int i=0; i < size; i++) {
            myList.add(i);
        }

        // Print out everything in list
        myList.stream().forEach(e -> System.out.print(e + " "));

        myList.stream().forEach(e -> System.out.print((e+1) + " "));

        myList.stream()
              .filter(e -> e % 2 == 0)
              .forEach(e -> System.out.print((e+1) + " "));

        // Two types of methods: intermediary, which produces another stream;
        // and terminal, which consumes the stream and returns a result.

        // Generate
        IntStream.range(0,500).forEach(e -> System.out.println(e));

        long before = System.currentTimeMillis();
        double answer = IntStream.range(0,10_000_000)
                                 .parallel()
                                 .mapToDouble(e -> Math.sin(e))
                                 .reduce(0, (a,b) -> a + b);
        System.out.println(answer);
        System.out.println("Time = " + (System.currentTimeMillis()-before));


        // Does nothing if no terminal
        before = System.currentTimeMillis();
        IntStream.range(0,10_000_000)
                                 .mapToDouble(e -> {
                                     System.out.println(e);
                                     return Math.sin(e);
                                 });
        System.out.println("Time = " + (System.currentTimeMillis()-before));

        // Watch out for race conditions
        total = 0;
        before = System.currentTimeMillis();
        IntStream.range(0,1000)
                 .parallel()
                 .mapToDouble(e -> {
                     int value = total;
                     try {
                         Thread.sleep(10);
                     } catch (InterruptedException e1) {
                         e1.printStackTrace();
                     }
                     total = value + 1;
                     return Math.sin(e);
                 })
                 .reduce(0, (a,b) -> a + b);
        System.out.println("Time = " + (System.currentTimeMillis()-before));
        System.out.println("Total = " + total);

    }
}
