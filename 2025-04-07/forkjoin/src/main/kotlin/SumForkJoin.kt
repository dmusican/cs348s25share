import kotlinx.coroutines.*

/**
 * This program recursively finds the sum of the sine values of an array in
 * parallel using Java's ForkJoin Framework. This example is originally from Dan
 * Grossman's A Sophomoric Introduction to Shared-Memory Parallelism and
 * Concurrency, Chapter 3. (Dave Musicant modified it a bit.)
 */

suspend fun sumTask(arr: List<Double>, lo: Int, hi: Int, seqCutoff: Int): Double {

    if (hi - lo <= seqCutoff) {
        var ans = 0.0
        for (numTimes in 0..<100) {
            for (i in lo..<hi) {
                ans += Math.sin(arr[i])
            }
        }
        return ans
    } else {
        val mid = (lo + hi) / 2
        // println("[${Thread.currentThread().name}] I am here")
        return coroutineScope {
            val left = async(CoroutineName("${lo}:${hi}")) { sumTask(arr, lo, mid, seqCutoff)}
                val right = sumTask(arr, mid, hi, seqCutoff)
                left.await() + right
        }

    }
}

/**
 * Sum the elements of an array.
 *
 * @param arr array to sum
 * @return sum of the array's elements
 * @throws InterruptedException
 *             shouldn't happen
 */
fun sumMultiThreaded(arr: List<Double>, seqCutoff: Int): Double =
    runBlocking(Dispatchers.Default) {
    async(CoroutineName("abc")) {
            sumTask(arr, 0, arr.count(), seqCutoff)
        }.await()
}
