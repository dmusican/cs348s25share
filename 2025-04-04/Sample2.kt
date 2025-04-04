class AnswerThread(var i: Int) : Thread() {
    var answer = 0

    override fun run() {
        for (j in 0..<100) {
            Thread.sleep(100)
            answer = answer + j
        }
    }
}

fun main() {
    val allThreads = mutableListOf<AnswerThread>()
    for (i in 0..<20) {
        // Only main thread has access to 'answer' in the object
        // because thread hasn't started yet
        val myThread = AnswerThread(i)
        myThread.start()
        // ok, uhoh, thread has started, main thread better
        // not touch answer
        allThreads.add(myThread)
    }

    // Get answers from each when done
    for (myThread in allThreads) {
        myThread.join()
        // main thread touches answer below, but only AFTER
        // thread is guaranteed to be done
        println("Answer from ${myThread.i} is ${myThread.answer}")
    }

}