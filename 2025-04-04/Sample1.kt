// Easier approach. ?????? hmmmm
// There's no clean way to return an answer
// from the threads
// So let's do something messy, bad, and fun, to show you what
// you shouldn't do, but some of you won't listen and will pay
import kotlin.concurrent.thread
fun main() {
    var counter = 0
    for (i in 0..<20) {
        val myThread = thread {
            val updatedCounter = counter + 1
            println("Hello from thread $i")
            counter = updatedCounter
            println("Hello again from thread $i. Counter is now $counter.")
        }
    }
    println("Final counter value $counter")
}