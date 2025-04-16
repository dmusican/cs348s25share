class Bank4(_balance: Int) {
    var balance = _balance
        set(value) {
            field = value
        }
        get() {
            return field
        }

    // Same as Bank4
    // all code in this method is in a synchronized
    // block with "this" as the object
    @Synchronized
    fun withdraw(amt: Int) {
            balance -= amt
    }

    @Synchronized
    fun deposit(amt: Int) {
            balance += amt
    }
}
