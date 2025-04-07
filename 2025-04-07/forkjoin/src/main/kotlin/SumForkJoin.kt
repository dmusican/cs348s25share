import kotlinx.coroutines.*

// This program recursively finds the sum of all of the prime values of an array
// in parallel using Java's ForkJoin Framework. This example is originally fom
// Dan Grossman's A Sophomoric Introduction to Shared-Memory Parallelism and
// Concurrency, Chapter 3. (Dave Musicant modified it a lot, and converted it to
// Kotlin.)

suspend fun sumTask(arr: List<Int>, lo: Int, hi: Int, seqCutoff: Int): Int {

    if (hi - lo <= seqCutoff) {
        var ans = 0
        for (i in lo..<hi) {
            ans += if (isPrime(arr[i])) 1 else 0
        }
        return ans
    } else {
        val mid = (lo + hi) / 2
        return coroutineScope {
            val left = async { sumTask(arr, lo, mid, seqCutoff)}
                val right = sumTask(arr, mid, hi, seqCutoff)
                left.await() + right
        }

    }
}

// Sum the prime values in an array.
fun sumMultiThreaded(arr: List<Int>, seqCutoff: Int): Int {
    return runBlocking(Dispatchers.Default) {
        async {
            sumTask(arr, 0, arr.count(), seqCutoff)
        }.await()
    }
}
