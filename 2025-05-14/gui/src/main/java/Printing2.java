import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Printing2 {

    public static void main(String[] args) {

        Observable.range(0, 10_000_000)
                  .map(e -> Math.sin(e))
                  .reduce(0.0, (a,b) -> a + b)
                  .subscribe(answer -> System.out.println(answer));


        Observable.interval(1, TimeUnit.SECONDS)
            .subscribe(item -> System.out.println(item));

        while(true);

    }
}
