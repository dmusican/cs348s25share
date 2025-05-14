import java.util.stream.IntStream
fun main() {

    IntStream
        .rangeClosed(1,10)
        .parallel()
        .map { it ->
            println("hey")
            it
        }
        .filter { number -> number % 2 == 0 }
        .forEach { thing -> println(thing)}

}
