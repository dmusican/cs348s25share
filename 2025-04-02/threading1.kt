// built in class named Thread that you inherit from
class ExampleThread(val id: Int) : Thread() {
    override fun run() {
        var total = 0
        for (num in 0..<10000000) {
            total += num
        }
        println("Hi from thread $id $total")
    }
}

fun main() {
    for (i in 0..<5) {
        val myThread = ExampleThread(i)
        myThread.start()

    }
}