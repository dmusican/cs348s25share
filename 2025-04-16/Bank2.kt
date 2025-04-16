class Bank2(_balance: Int) {
    private val bankLock = "hellogoodfriend"
    var balance = _balance
        set(value) {
            field = value
        }
        get() {
            return field
        }

    fun withdraw(amt: Int) {
        synchronized {
            balance -= amt
        }
    }

    fun deposit(amt: Int) {
        synchronized {
            balance += amt
        }
    }
}
