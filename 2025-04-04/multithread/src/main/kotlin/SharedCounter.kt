

var globalCounter = 0


/**
 * Add to a shared counter.
 */
fun increment(numThreads: Int, numIncrementsPerThread: Int): Int {

    globalCounter += numThreads * numIncrementsPerThread
    return globalCounter
}
