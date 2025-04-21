import kotlin.concurrent.thread

class BankAccount(var balance: Int) {

    @Synchronized                         // line A
    fun withdraw(amt: Int) {
        balance -= amt                    // line B
    }                                     // line C

    @Synchronized                         // line D
    fun deposit(amt: Int) {
        balance += amt;                   // line E
    }                                     // line F

    fun transferTo(amt: Int, other: BankAccount) {
        synchronized (this) {
            synchronized (other) {
                this.withdraw(amt)
                other.deposit(amt)
            }
        }
    }
}

fun main() {
    val one = BankAccount(1000)
    val two = BankAccount(2000)

    val t1 = thread {
        while (true) {
            println("1: About to transfer $500")
            one.transferTo(500,two)
            println("1: Transferred. Balance is ${one.balance}.")
        }
    }

    val t2 = thread {
        while (true) {
            println("  2: About to transfer $100")
            two.transferTo(100,one)
            println("  2: Transferred. Balance is ${two.balance}.")
        }
    }

    t1.start()
    t2.start()
}
