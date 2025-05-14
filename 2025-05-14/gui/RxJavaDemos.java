import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class RxJavaDemos {

    public static void main(String[] args) {

        // Not the same as a java.util.Observable, be careful
        // Observable is a series of items that push
        Observable<Integer> source = Observable.just(1, 2, 3, 4, 5);
        source.subscribe(System.out::println);
        source.subscribe(e -> System.out.println(e));

        // Better to make sure to handle exceptions
        Observable<Integer> source2 = Observable.range(1,5);
        source2.subscribe(System.out::println, Throwable::printStackTrace);


        Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(e -> {System.out.println(Thread.currentThread().getName() + " " + e);});

        // Observable.range(0, 10_000_000)
        //         .map(e -> Math.sin(e))
        //         .reduce(0.0, (a,b) -> a + b)
        //         .subscribe(System.out::println);

        // // Can use blockingGet to get the answer, but defeats whole purpose of
        // // letting it decide when it wants to do things; big difference from
        // // Java streams is you can wait for things to become available. Watch
        // // this:
        // double answer = Observable.range(0, 10_000_000)
        //         .map(e -> Math.sin(e))
        //         .reduce(0.0, (a,b) -> a + b)
        //         .blockingGet();

        // System.out.println(answer);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
