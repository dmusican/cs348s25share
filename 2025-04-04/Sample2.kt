class AnswerThread(var i: Int) : Thread() {
    var answer = 0

    override fun run() {
        for (j in 0..<100) {
            Thread.sleep(1)
            answer = answer + j
        }
    }
}

fun main() {
    val allThreads = mutableListOf<AnswerThread>()
    for (i in 0..<20) {
        val myThread = AnswerThread(i)
        myThread.start()
        allThreads.add(myThread)
    }
    for (myThread in allThreads) {
        myThread.join()  // wait for thread to finish
    }

    // Get answers from each when done
    for (myThread in allThreads) {
        println("Answer from ${myThread.i} is ${myThread.answer}")
    }

}