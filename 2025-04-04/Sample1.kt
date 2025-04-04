// Easier approach. ?????? hmmmm
import kotlin.concurrent.thread
fun main() {
    for (i in 0..<20) {
        val myThread = thread {
            println("Hello from thread $i")
        }
    }
}