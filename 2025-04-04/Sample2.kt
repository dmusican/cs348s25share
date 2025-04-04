class AnswerThread(var i: Int) : Thread() {
    var answer = 0

    override fun run() {
        for (j in 0..<10000) {
            answer = answer + j
        }
    }
}

fun main()