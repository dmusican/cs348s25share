import kotlin.concurrent.thread
var thing = ThreadLocal<Int>() {
    override fun initialValue(): Int {
        return 0
    }
}

fun main() {
    val t1 = thread {
        thing = 8
        println("thing is $thing")
    }
    t1.join()
    println(thing)
}
