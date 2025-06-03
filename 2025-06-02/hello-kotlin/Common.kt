import java.rmi.Remote
import java.rmi.RemoteException
import java.io.Serializable

class Counter : Serializable {
    var value = 0

    fun add() {
        value++
    }
}

interface Hello : Remote {
    @Throws(RemoteException::class)
    fun sayHello(increment: Int, counter: Counter): String
}



