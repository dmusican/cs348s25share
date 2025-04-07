import kotlinx.coroutines.*
import kotlin.random.Random
import kotlin.time.measureTimedValue
import java.util.concurrent.RecursiveTask
import java.util.concurrent.ForkJoinPool

fun seqSubset(arr: Array<Double>, lo: Int, hi: Int): Double {
    var sum = 0.0
    for (i in lo..<hi) {
        sum += Math.sin(arr[i])
    }
    return sum
}

suspend fun suspendSumSubset(arr: Array<Double>, lo: Int, hi: Int): Double {
    var sum = 0.0
    for (i in lo..<hi) {
        sum += Math.sin(arr[i])
    }
    return sum
}

suspend fun coroutineRecursive(arr: Array<Double>, lo: Int, hi: Int): Double {

    // Cutoff to try to avoid some overhead
    if (hi - lo <= 10000) {
        var ans = 0.0
        for (i in lo..<hi) {
            ans += Math.sin(arr[i])
        }
        return ans
    } else {
        val mid = (lo + hi) / 2
        return withContext(currentCoroutineContext()) {
                val left = async { coroutineRecursive(arr, lo, mid)}
                val right = coroutineRecursive(arr, mid, hi)
                left.await() + right
            }
    }
}
class SumTask(var arr: Array<Double>, var lo: Int, var hi: Int): RecursiveTask<Double>() {

    override fun compute(): Double {
        var sum = 0.0
        for (i in lo..<hi) {
            sum += Math.sin(arr[i])
        }
        return sum
    }
}

class SumTaskRecursive(var arr: Array<Double>, var lo: Int, var hi: Int): RecursiveTask<Double>() {

    override fun compute(): Double {
        // Cutoff to try to avoid some overhead
        if (hi - lo <= 10000) {
            var ans = 0.0
            for (i in lo..<hi) {
                ans += Math.sin(arr[i])
            }
            return ans
        } else {
            val mid = (lo + hi) / 2
            val left = SumTaskRecursive(arr, lo, mid)
            val right = SumTaskRecursive(arr, mid, hi)
            left.fork()
            val rightAns = right.compute();
            val leftAns = left.join()
            return leftAns + rightAns
        }
    }
}


fun main() {
    for (size in listOf(100_000, 1_000_000, 10_000_000, 100_000_000)) {
        println("---------size ${size}-------------")

        var r = Random(90125)
        val arr = Array<Double>(size){r.nextDouble()};

        // Warmup the array
        val temp = seqSubset(arr, 0, size/2) + seqSubset(arr, size/2, size)

        // Add up both halves, sequentially
        val (seqSum, seqTime) = measureTimedValue {
            seqSubset(arr, 0, size/2) + seqSubset(arr, size/2, size)
        }
        println("Sequential: Sum = ${seqSum}, Time = ${seqTime}")

        // Add up both halves, in parallel using forkjoin
        val (forkjoinSum, forkjoinTime) = measureTimedValue {
            val pool = ForkJoinPool.commonPool()
            val first = pool.submit(SumTask(arr, 0, size/2))
            val second = pool.submit(SumTask(arr, size/2, size))
            first.get() + second.get()
        }

        println("Forkjoin:   Sum = ${forkjoinSum}, Time = ${forkjoinTime}")

        // Recursive version
        val (recForkjoinSum, recForkjoinTime) = measureTimedValue {
            val pool = ForkJoinPool.commonPool()
            val first = pool.submit(SumTaskRecursive(arr, 0, size/2))
            val second = pool.submit(SumTaskRecursive(arr, size/2, size))
            first.get() + second.get()
        }

        println("Rec Fjoin:  Sum = ${recForkjoinSum}, Time = ${recForkjoinTime}")

        // Add up both halves, in parallel using coroutines
        val (parallelSum, parallelTime) = measureTimedValue {
            runBlocking(Dispatchers.Default) {
                val one = async { suspendSumSubset(arr, 0, size/2) }
                val two = async { suspendSumSubset(arr, size/2, size) }
                one.await() + two.await()
            }
        }
        println("Coroutine:  Sum = ${parallelSum}, Time = ${parallelTime}")

        // Recursive coroutine version
        val (recCoSum, recCoTime) = measureTimedValue {
            runBlocking(Dispatchers.Default) {
                val one = async { coroutineRecursive(arr, 0, size/2) }
                val two = async { coroutineRecursive(arr, size/2, size) }
                one.await() + two.await()
            }
        }
        println("RecCorou:   Sum = ${recCoSum}, Time = ${recCoTime}")
    }
}
