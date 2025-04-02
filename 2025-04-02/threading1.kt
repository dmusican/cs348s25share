// built in class named Thread that you inherit from
class ExampleThread : Thread() {
    override fun run() {
        println("Hi from thread")
    }
}

fun main() {
    for (i in 0..<5) {
        val myThread = ExampleThread()
        myThread.start()
        
    }
}