// built in class named Thread that you inherit from
class ExampleThread(val id: Int) : Thread() {
    override fun run() {
        println("Hi from thread $id")
    }
}

fun main() {
    for (i in 0..<5) {
        val myThread = ExampleThread(i)
        myThread.start()

    }
}