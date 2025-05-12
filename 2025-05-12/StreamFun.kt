fun main() {

    val myList = mutableListOf<Int>()
    for (i in 0..<1000) {
        myList.add(i)
    }

    // Streaming API: gives you iteration
    // and other capabilities as function/method calls
    // Very similar to higher order functions in Scheme
    // (if you've seen that)
    myList.stream().parallel().forEach { print("$it ") }
    println()
}
