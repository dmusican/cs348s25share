import java.util.concurrent.locks.ReentrantLock

fun main() {
    val myLock = ReentrantLock()
    try {
        myLock.lock()
        // thing
        // suppose an exception happens here
        // more thing
        // more thing
    } finally {
        myLock.unlock()
        // lots of other things
    }

}
