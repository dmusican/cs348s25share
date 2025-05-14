import java.util.stream.IntStream
fun main() {

    IntStream
        .rangeClosed(1,10)
        // .parallel()
        .filter { number -> number % 2 == 0 }
        .forEach { thing -> println(thing)}
        .forEach { thing -> println(thing)}

}
