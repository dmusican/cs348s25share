import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.random.Random
import kotlin.time.measureTimedValue
import kotlin.math.sin

class MaxValueTest {
    
    fun sequentialMax(arr: List<Double>): Double {
        var max = Double.NEGATIVE_INFINITY
        for (i in 0..<arr.count()) {
            val sinValue = Math.sin(arr[i])
            if (sinValue > max) {
                max = sinValue
            }
        }
        return max
    }
    
    @Test
    fun testMax()  {
        var r = Random(90125)
        val arr = mutableListOf<Double>()
        for (i in 0..<40_000_000) {
            arr.add(r.nextDouble())
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

        val numProcessors = Runtime.getRuntime().availableProcessors()
        println("Number of processors = ${numProcessors}")
        for (numThreads in 1..(numProcessors+2)) {
            println("-----------------------------")
            println("Number of threads: ${numThreads}")
            
            // Do the parallel test a number of times to warm it up, measure on last
            total = 0.0
            for (i in 0..<numWarmups) {
                total += maxMultiThreaded(arr, numThreads)
            }
            val (parallelMax, parallelTime) = measureTimedValue {
                maxMultiThreaded(arr, numThreads)
            }
            // println("Warmup parallel total = ${total}")
            println("Sequential max = ${seqMax}")
            println("Parallel max: ${parallelMax}")
            
            println("Sequential time = ${seqTime}")
            println("Parallel time = ${parallelTime}")
            val speedup = seqTime/parallelTime
            println("Speedup = ${speedup}")
            assertEquals(seqMax, parallelMax, 0.001)
        }
    }
}
