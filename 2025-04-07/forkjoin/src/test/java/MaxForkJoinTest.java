import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MaxForkJoinTest {

    private double sequentialMax(int[] arr) {
        double max = Double.NEGATIVE_INFINITY;
        for (int i=0; i < arr.length; i++) {
            double sinValue = Math.sin(arr[i]);
            if (sinValue > max) {
                max = sinValue;
            }
        }
        return max;
    }

    @Test
    public void testMax() throws Exception {
        Random r = new Random(90125);
        int[] arr = new int[10_000_000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = r.nextInt();
        }

        // Do the sequential test a number of times to warm it up, measure on
        // last
        long timeBefore = 0;
        int numWarmups = 3;
        long seqTime = 0;
        double seqMax = 0;
        for (int i=0; i < numWarmups; i++) {
            timeBefore = System.currentTimeMillis();
            seqMax = sequentialMax(arr);
            seqTime = System.currentTimeMillis() - timeBefore;
        }

        double parallelMax = 0;
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
                parallelMax = MaxForkJoin.max(arr, seqCutoff);
                parallelTime = System.currentTimeMillis() - timeBefore;
            }
            assertEquals(seqMax, parallelMax, 1e-9);
            double speedup = ((double) seqTime) / parallelTime;
            System.out.printf("%10d %5d %5d %7.3f\n", seqCutoff, seqTime,
                              parallelTime, speedup);
        }
    }
}
