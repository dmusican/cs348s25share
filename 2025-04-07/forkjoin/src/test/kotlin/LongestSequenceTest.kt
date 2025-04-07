import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.random.Random
import kotlin.time.measureTimedValue


class LongestSequenceTest {

    fun sequentialCount(arr: List<Int>): Int {
        var greatestCount = 0
        var localCount = 0
        for (i in 0..<arr.count()) {
            if (isPrime(arr[i])) {
                localCount++
                if (localCount > greatestCount) {
                    greatestCount = localCount
                }
            } else {
                localCount = 0
            }
        }
        return greatestCount
    }

    @Test
    fun testTrivialLongestSequence() {
        val arr = listOf(2, 18, 11, 7, 17, 12, 10, 23, 14, 11)
        val runSize = longestSequence(arr, 100)
        assertEquals(3, runSize)
    }

    @Test
    fun testLongestSequence() {
        var r = Random(90125)
        val arr = mutableListOf<Int>()
        for (i in 0..<50_000) {
            // Make all of the numbers odd; gives them a better chance of being
            // prime
            arr.add((r.nextInt(400_000)*2)+1)
        }

        // Do the sequential test a number of times to warm it up, then do it
        // one more time and measure on last
        val numWarmups = 3
        var total = 0
        for (i in 0..<numWarmups) {
            total += sequentialCount(arr)
        }
        val (seqResult, seqTime) = measureTimedValue {
            sequentialCount(arr)
        }
        // Print the warmup total just to make sure that the code
        // doesn't get optimized out
        println("Warmup sequential total = ${total}")
        println("Max sequence length = ${seqResult}")
        println("Measured sequential time = ${seqTime}")

        val numProcessors = Runtime.getRuntime().availableProcessors()
        println("Number of processors = ${numProcessors}")
        println("-----------------------------")
        val seqCutoffs = listOf(
            1, 2, 3, 4, 10
        )
        println("cutoff / seq time / parallel time / speedup")

        for (seqCutoff in seqCutoffs) {
            // Do the parallel test a number of times to warm it up, measure on
            // last
            total = 0
            for (i in 0..<numWarmups) {
                total += longestSequence(arr, seqCutoff)
            }
            val (parallelResult, parallelTime) = measureTimedValue {
                longestSequence(arr, seqCutoff)
            }

            val speedup = seqTime/parallelTime
            println(String.format("%10d %s %s %7.3f", seqCutoff, seqTime,
                                  parallelTime, speedup))
            assertEquals(seqResult, parallelResult)
        }
    }
}
