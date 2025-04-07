import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.random.Random
import kotlin.time.measureTimedValue


class SumForkJoinTest {

    fun sequentialTotal(arr: List<Double>): Double {
        var sum = 0.0
        for (numTimes in 0..<100) {
            for (i in 0..<arr.count()) {
                sum += Math.sin(arr[i])
            }
        }
        return sum
    }


    @Test
    fun testSum() {
        var r = Random(90125)
        val arr = mutableListOf<Double>()
        for (i in 0..<100_000) {
        // for (i in 0..<10_000) {
            arr.add(r.nextDouble())
        }

        // Do the sequential test a number of times to warm it up, then do it
        // one more time and measure on last
        val numWarmups = 3
        // total each time; display it to make sure it doesn't get
        // optimized out
        var total = 0.0
        for (i in 0..<numWarmups) {
            total += sequentialTotal(arr)
        }
        val (seqSum, seqTime) = measureTimedValue {
            sequentialTotal(arr)
        }
        println("Warmup sequential total = ${total}")




        val numProcessors = Runtime.getRuntime().availableProcessors()
        println("Number of processors = ${numProcessors}")
        // for (numThreads in 1..(numProcessors+2)) {
            println("-----------------------------")
            // println("Number of threads: ${numThreads}")

            // val seqCutoffs = listOf(
            //     1000, 10000, 100_000, 1_000_000, 5_000_000
            // )
            val seqCutoffs = listOf(
                1, 2, 3, 4, 10, 100, 1000, 10000, 100_000, 1_000_000, 5_000_000
            )
            println("cutoff / seq time / parallel time / speedup")

            for (seqCutoff in seqCutoffs) {
                // Do the parallel test a number of times to warm it up, measure on last
                total = 0.0
                for (i in 0..<numWarmups) {
                    total += sumMultiThreaded(arr, seqCutoff)
                }
                val (parallelSum, parallelTime) = measureTimedValue {
                    sumMultiThreaded(arr, seqCutoff)
                }

                val speedup = seqTime/parallelTime
                println(String.format("%10d %s %s %7.3f", seqCutoff, seqTime,
                                      parallelTime, speedup))
                assertEquals(seqSum, parallelSum, 1e-3)
            }
        // }
    }
}
