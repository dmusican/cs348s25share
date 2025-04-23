import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class Drop {
    var message = ""
    var full = false
    val lock = ReentrantLock()
    val condition = lock.newCondition()

    fun take(): String {
        lock.withLock {
            while (!full) condition.await()
            full = false
            condition.signalAll()
            return message
        }
    }

    fun put(message: String) {
        lock.withLock {
            while (full) condition.await()
            full = true
            this.message = message
            condition.signalAll()
        }
    }
}


var drop = Drop()

class Producer : Thread() {
    var count = 0
    override fun run() {
        while (true) {
            drop.put("${threadId()} $count")
            count++
        }
    }
}

class Consumer : Thread() {
    override fun run() {
        while (true) println("Taking ${drop.take()}")
    }
}


fun main() {
    Producer().start()
    Producer().start()
    Consumer().start()
}
