
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

// Port of code from Java example at
// https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/locks/ReentrantReadWriteLock.html

class RWDictionary {
    val m = mutableMapOf<String, String>()
    val rwl = ReentrantReadWriteLock()

    fun lookup(key: String): String? {
        return rwl.read {
            m.get(key)
        }
    }

    fun add(key: String, value: String) {
        rwl.write {
            m.put(key, value)
        }
    }

    fun clear() {
        rwl.write {
            m.clear()
        }
    }
}
