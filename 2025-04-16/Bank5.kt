class Bank4(_balance: Int) {
    var balance = _balance
        set(value) {
            field = value
        }
        get() {
            return field
        }

    fun withdraw(amt: Int) {
        synchronized(this) {
            balance -= amt
        }
    }

    fun deposit(amt: Int) {
        synchronized(this) {
            balance += amt
        }
    }
}
