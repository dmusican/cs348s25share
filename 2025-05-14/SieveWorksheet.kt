import java.util.stream.IntStream;

fun main() {
    // val limit = 500000000 // Limit to find prime numbers up to
    val limit = 50 // Limit to find prime numbers up to

    // Create a boolean array "isPrime" and initialize all entries as true
    val isPrime= Array<Boolean>(limit+1) { true }

    IntStream
        // Use all integers up to the square root of the limit as the sieve choices
        .rangeClosed(2, Math.sqrt(limit.toDouble()).toInt())

        // Use only those that are know to be prime
        .filter { number -> isPrime[number] }

        // Set all multiples of that prime within range to be not prime
        // Don't use a direct loop, do it via streams
        .forEach { prime -> IntStream.rangeClosed(prime, limit/prime)
                                .forEach { index ->  isPrime[index*prime] = false } }


    IntStream.rangeClosed(2, limit)
    // Keep just the prime numbers
        .filter { i -> isPrime[i] }
        .forEach { i -> println(i) }

    val count = IntStream.rangeClosed(2, limit)

    // Keep just the prime numbers
        .filter { i -> isPrime[i] }
        .count()

    println("Number of primes up to $limit: $count")
}


// Followup questions:
// Where can .parallel() be added to speed things up?
//
// Where is a place that someone might be tempted to add .parallel(), but would
// just make things worse?
