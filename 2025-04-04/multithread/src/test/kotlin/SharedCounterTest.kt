import kotlin.test.Test
import kotlin.test.assertNotEquals

class SharedCounterTest {

    @Test
    fun testCounter()  {
        var aggregateTotal = 0
        val numIterations = 10
        for (numtimes in 0..<numIterations) {
            var total = 0
            for (i in 0 ..< 10) {
                globalCounter = 0
                val result = increment(20, 10)
                total += result
            }
            println("Actual total ${total}")
            aggregateTotal += total
        }
        assertNotEquals(2000*numIterations, aggregateTotal)
    }
}
