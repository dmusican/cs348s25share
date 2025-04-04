/**
* This thread finds the sum of the sine values of a subsection of an array.
* I know, summing the sine values (i.e., Math.sin) seems like a silly task.
* I'm doing it because calculating the sine value is nice and slow. That helps
* make the parallelism pay off.
*/
class SumThread(val arr: List<Double>, val lo: Int, val hi: Int): Thread() {
    var ans = 0.0
    
    override fun run() {
        for (i in lo..<hi) {
            ans += Math.sin(arr[i])
        }
    }
}

/**
* Sum the sine of the elements of an array.
*/
fun sumMultiThreaded(arr: List<Double>, numThreads: Int): Double {
    val len = arr.count()
    var ans = 0.0
    
    // Create and start threads.
    val ts = mutableListOf<SumThread>()
    for (i in 0..<numThreads) {
        val thread = SumThread(
            arr,
            (i * len) / numThreads,
            ((i + 1) * len / numThreads))
        ts.add(thread)
        thread.start()
    }
    
    // Wait for the threads to finish and sum their results.
    for (t in ts) {
        t.join()
        ans += t.ans
    }
    return ans
}
