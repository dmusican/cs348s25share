import java.util.concurrent.locks.ReentrantLock

fun main() {
    val myLock = ReentrantLock()
    myLock.lock()
    // thing
    // suppose an exception happens here
    // more thing
    // more thing
    myLock.unlock()
    // lots of other things

}
