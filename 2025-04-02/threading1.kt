// built in class named Thread that you inherit from
class ExampleThread(val id: Int) : Thread() {
    override fun run() {
        var total = 0.0
        for (num in 0..<1000000000) {
            total += Math.sin(num+0.0)
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