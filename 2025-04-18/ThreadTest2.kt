// https://dzone.com/articles/java-volatile-keyword0-


import kotlin.concurrent.thread
import kotlin.random.Random
var sharedValue = 0

fun main() {
    thread {
        var localValue = sharedValue
        var count = 0L
        while (true){
            if( localValue != sharedValue){
                println("Read new sharedValue : $sharedValue")
                println("Count = $count")
                localValue = sharedValue;
            }
            count++
            if (count % 10000000000L == 0L) {
                System.exit(1)
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
