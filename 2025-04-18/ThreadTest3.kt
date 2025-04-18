// https://dzone.com/articles/java-volatile-keyword0-


import kotlin.concurrent.thread
import kotlin.random.Random
@Volatile
var sharedValue = 0

fun main() {
    thread {
        var localValue = sharedValue
        while (true){
            if( localValue != sharedValue){
                println("Read new sharedValue : $sharedValue")
                localValue = sharedValue;
            }
        }
    }

    thread {
        var localValue = sharedValue
        while (true){
            localValue++
            println("Incrementing sharedValue to $localValue")
            sharedValue = localValue
            Thread.sleep(500)
        }
    }
}
