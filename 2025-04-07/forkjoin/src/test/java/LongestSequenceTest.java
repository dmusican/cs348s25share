import org.junit.Test;

import static org.junit.Assert.assertEquals;
import java.util.Random;


public class LongestSequenceTest {

    private int sequentialCount(int item, int[] arr) {
        int greatestCount = 0;
        int localCount = 0;
        for (int i=0; i < arr.length; i++) {
            if (LongestSequenceForkJoin.compare(arr[i], item)) {
                localCount++;
                if (localCount > greatestCount) {
                    greatestCount = localCount;
                }
            } else {
                localCount = 0;
            }
        }
        return greatestCount;
    }

    @Test
    public void testTrivialLongestSequence() throws Exception {
        int[] arr = {2, 17, 17, 8, 17, 17, 17, 0, 17, 1};
        int runSize = LongestSequenceForkJoin.longestSequence(17, arr, 100);
        assertEquals(3, runSize);
    }

    @Test
    public void testLongestSequence() throws Exception {
        Random r = new Random(90125);
        int[] arr = new int[10_000_000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = r.nextInt(3);
        }
        int lookupValue = 2;

        // Do the sequential test a number of times to warm it up, measure on
        // last
        int numWarmups = 3;
        long timeBefore = 0;
        long seqTime = 0;
        int seqCount = 0;
        for (int i=0; i < numWarmups; i++) {
            timeBefore = System.currentTimeMillis();
            seqCount = sequentialCount(lookupValue, arr);
            seqTime = System.currentTimeMillis() - timeBefore;
        }

        int parallelCount = 0;
        long parallelTime = 0;

        // Do the parallel test a number of times to warm it up, measure on last
        // int[] seqCutoffs = {1, 2, 10, 100, 1000, 10000};
        int[] seqCutoffs = {
            1, 2, 3, 4, 10, 100, 1000, 10000, 100_000, 1_000_000, 5_000_000
        };
        for (int seqCutoff : seqCutoffs) {
            for (int i=0; i < numWarmups; i++) {
                timeBefore = System.currentTimeMillis();
                parallelCount = LongestSequenceForkJoin
                    .longestSequence(lookupValue, arr, seqCutoff);
                parallelTime = System.currentTimeMillis() - timeBefore;
            }
            assertEquals(seqCount, parallelCount, 1e-9);
            double speedup = ((double) seqTime) / parallelTime;
            System.out.printf("%10d %5d %5d %7.3f\n", seqCutoff, seqTime,
                              parallelTime, speedup);
        }
    }
}
