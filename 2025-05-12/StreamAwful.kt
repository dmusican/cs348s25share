import java.util.stream.IntStream
fun main() {

    var total = 0
    val answer = IntStream.range(0, 1_000_000)
        .parallel()
        .mapToDouble {
            val value = total
            Thread.sleep(1)
            total = value + 1
            Math.sin(it.toDouble()) }
        .reduce { a,b -> a + b }
    println(answer)
}
