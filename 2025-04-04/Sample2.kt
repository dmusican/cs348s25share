class AnswerThread(var i: Int) : Thread() {
    var answer = 0

    override fun run() {
        for (j in 0..<10000) {
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
    // Get answers from each when done
    // bad below, wait we'll fix
    for (myThread in allThreads) {
        println("Answer from ${myThread.i} is ${myThread.answer}")
    }

}