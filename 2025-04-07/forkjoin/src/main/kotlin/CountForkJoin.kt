import kotlinx.coroutines.*

// This program recursively finds the count of all of the prime values of an array
// in parallel using Java's ForkJoin Framework.

suspend fun countTask(arr: List<Int>, lo: Int, hi: Int, seqCutoff: Int): Int {

    if (hi - lo <= seqCutoff) {
        var ans = 0
        for (i in lo..<hi) {
            ans += if (isPrime(arr[i])) 1 else 0
        }
        return ans
    } else {
        val mid = (lo + hi) / 2
        return coroutineScope {
            val left = async { countTask(arr, lo, mid, seqCutoff)}
            val right = countTask(arr, mid, hi, seqCutoff)
            left.await() + right
        }

    }
}

// Sum the prime values in an array.
fun countMultiThreaded(arr: List<Int>, seqCutoff: Int): Int {
    return runBlocking(Dispatchers.Default) {
        async {
            countTask(arr, 0, arr.count(), seqCutoff)
        }.await()
    }
}
