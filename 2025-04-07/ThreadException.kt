import kotlin.concurrent.thread
fun doStuff() {
    for (i in 0..<200) {
        val myThread = thread {
            var counter = 0
            for (i in 0..<1000) {
                Thread.sleep(1)
                counter++
            }
            println("Hello from thread $i. Counter is now $counter.")
        }
    }
    val newThread = thread {
        throw Exception("oops")
    }
}

fun main() {
    doStuff()
    println("Successful")
}
