fun main() {

    val myList = mutableListOf<Int>()
    for (i in 0..<1000) {
        myList.add(i)
    }

    // Streaming API: gives you iteration
    // and other capabilities as function/method calls
    // Very similar to higher order functions in Scheme
    // (if you've seen that)
    //
    // parallel: functions that follow execute
    // concurrently separately for each item
    // in the stream
    // using the Java ForkJoin thread pool
    // myList.stream().parallel().forEach { print("$it ") }
    // println()

    // More calculations
    val answer = IntStream.range(0, 10_000_000)
        .mapToDouble { Math.sin(it.toDouble()) }
        .reduce { a,b -> a + b )
    println(answer)
}
