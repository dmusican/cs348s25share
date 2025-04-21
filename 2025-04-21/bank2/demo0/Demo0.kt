class Drop {
    var message = ""
    var full = false

    @Synchronized
    fun take(): String {
        if (full) {
            full = false
            return message
        } else {
            return "Nothing there"
        }
    }

    @Synchronized
    fun put(message: String) {
        if (!full) {
            full = true
            this.message = message
        }
    }
}


var drop = Drop()

class Producer : Thread() {
    var count = 0
    override fun run() {
        while (true) {
            println("${threadId()} putting $count")
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
