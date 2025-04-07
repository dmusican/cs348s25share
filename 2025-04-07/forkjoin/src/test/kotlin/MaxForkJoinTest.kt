import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.random.Random
import kotlin.time.measureTimedValue


class MaxForkJoinTest {

    fun sequentialMax(arr: List<Int>): Int {
        var ans = Int.MIN_VALUE
        for (i in 0..<arr.count()) {
            if (isPrime(i) && i > ans) {
                ans = i
            }
        }
        return ans
    }

    @Test
    fun testMax() {
        var r = Random(90125)
        val arr = mutableListOf<Int>()
        for (i in 0..<100_000) {
            arr.add(r.nextInt(10000))
        }

        // Do the sequential test a number of times to warm it up, then do it
        // one more time and measure on last
        val numWarmups = 3
        // total each time; display it to make sure it doesn't get
        // optimized out
        var total = 0.0
        for (i in 0..<numWarmups) {
            total += sequentialMax(arr)
        }
        val (seqMax, seqTime) = measureTimedValue {
            sequentialMax(arr)
        }
        // Print the warmup total just to make sure that the code
        // doesn't get optimized out
        println("Warmup sequential max total = ${total}")
        println("Largest prime in list = ${seqMax}")
        println("Measured sequential time = ${seqTime}")

        val numProcessors = Runtime.getRuntime().availableProcessors()
        println("Number of processors = ${numProcessors}")
        println("-----------------------------")
        val seqCutoffs = listOf(
            1, 2, 3, 4, 10, 100, 1000, 10_000, 50_000, 100_000
        )
        println("cutoff / seq time / parallel time / speedup")

        for (seqCutoff in seqCutoffs) {
            // Do the parallel test a number of times to warm it up, measure on
            // last
            total = 0.0
            for (i in 0..<numWarmups) {
                total += sumMultiThreaded(arr, seqCutoff)
            }
            val (parallelMax, parallelTime) = measureTimedValue {
                maxMultiThreaded(arr, seqCutoff)
            }

            val speedup = seqTime/parallelTime
            println(String.format("%10d %s %s %7.3f", seqCutoff, seqTime,
                                  parallelTime, speedup))
            assertEquals(seqMax, parallelMax)
        }
    }
}
