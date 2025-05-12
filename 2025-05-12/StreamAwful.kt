import java.util.stream.IntStream

@Volatile
var total = 0
fun main() {

    val answer = IntStream.range(0, 1_000)
        .parallel()
        .mapToDouble {
            val value = total
            Thread.sleep(1)
            total = value + 1
            Math.sin(it.toDouble()) }
        .reduce { a,b -> a + b }
    println(answer)
    println(total)
}
