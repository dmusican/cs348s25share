import java.util.concurrent.locks.ReentrantLock

fun main() {
    val myLock = ReentrantLock()
    myLock.lock()
    // thing
    // more thing
    // more thing
    myLock.unlock()
    // lots of other things

}
