class Bank4(_balance: Int) {
    var balance = _balance
        set(value) {
            field = value
        }
        get() {
            return field
        }

    @Synchronized
    fun withdraw(amt: Int) {
            balance -= amt
    }

    @Synchronized
    fun deposit(amt: Int) {
            balance += amt
    }
}
