import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SumForkJoinTest {

    private double sequentialTotal(int[] arr) {
        double sum = 0;
        for (int i=0; i < arr.length; i++) {
            sum += Math.sin((arr[i]));
        }
        return sum;
    }

    @Test
    public void testSum() throws Exception {
        Random r = new Random(90125);
        int[] arr = new int[10_000_000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = r.nextInt();
        }
        // Do the sequential test a number of times to warm it up, measure on
        // last
        int numWarmups = 3;
        long timeBefore = 0;
        long seqTime = 0;
        double seqSum = 0;
        for (int i=0; i < numWarmups; i++) {
            timeBefore = System.currentTimeMillis();
            seqSum = sequentialTotal(arr);
            seqTime = System.currentTimeMillis() - timeBefore;
        }

        double parallelSum = 0;
        long parallelTime = 0;

        // Do the parallel test a number of times to warm it up, measure on last
        // int[] seqCutoffs = {1, 2, 10, 100, 1000, 10000};
        int[] seqCutoffs = {
            1, 2, 3, 4, 10, 100, 1000, 10000, 100_000, 1_000_000, 5_000_000
        };
        System.out.println("cutoff / seq time / parallel time / speedup");
        for (int seqCutoff : seqCutoffs) {
            // Do the parallel test a number of times to warm it up, measure on
            // last
            for (int i = 0; i < numWarmups; i++) {
                timeBefore = System.currentTimeMillis();
                parallelSum = SumForkJoin.sum(arr, seqCutoff);
                parallelTime = System.currentTimeMillis() - timeBefore;
            }
            assertEquals(seqSum, parallelSum, 1e-9);
            double speedup = ((double) seqTime) / parallelTime;
            System.out.printf("%10d %5d %5d %7.3f\n", seqCutoff, seqTime,
                              parallelTime, speedup);
        }
    }
}
