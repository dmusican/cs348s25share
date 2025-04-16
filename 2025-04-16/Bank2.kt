class Bank2(_balance: Int) {

    var balance = _balance
        set(value) {
            field = value
        }
        get() {
            return field
        }

    fun withdraw(amt: Int) {
        balance -= amt
    }

    fun deposit(amt: Int) {
        balance += amt
    }
}
