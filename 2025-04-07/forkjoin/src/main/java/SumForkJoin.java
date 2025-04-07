import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * This program recursively finds the sum of the sine values of an array in
 * parallel using Java's ForkJoin Framework. This example is originally from Dan
 * Grossman's A Sophomoric Introduction to Shared-Memory Parallelism and
 * Concurrency, Chapter 3. (Dave Musicant modified it a bit.)
 */
class SumTask extends RecursiveTask<Double> {

    // This just makes a warning in some environments go away; the super class
    // is serializable and Java warns that it wants a serial id
    private static final long serialVersionUID = -4506626749254389321L;

    public static final int SEQUENTIAL_THRESHOLD = 10;

    private int lo, hi;
    private int[] arr;
    private int seqCutoff;

    public SumTask(int[] arr, int lo, int hi, int seqCutoff) {
        this.lo = lo;
        this.hi = hi;
        this.arr = arr;
        this.seqCutoff = seqCutoff;
    }

    @Override
    public Double compute() {
        if (hi - lo <= seqCutoff) {
            double ans = 0;
            for (int i = lo; i < hi; i++) {
                ans += Math.sin(arr[i]);
            }
            return ans;
        } else {
            int mid = (lo + hi) / 2;
            SumTask left = new SumTask(arr, lo, mid, seqCutoff);
            SumTask right = new SumTask(arr, mid, hi, seqCutoff);
            left.fork();
            double rightAns = right.compute();
            double leftAns = left.join();
            return leftAns + rightAns;
        }
    }
}

public class SumForkJoin {

    /**
     * Sum the elements of an array.
     *
     * @param arr array to sum
     * @return sum of the array's elements
     * @throws InterruptedException
     *             shouldn't happen
     */
    public static double sum(int[] arr, int seqCutoff)
        throws InterruptedException {
        return ForkJoinPool.commonPool()
            .invoke(new SumTask(arr, 0, arr.length, seqCutoff));
    }
}
