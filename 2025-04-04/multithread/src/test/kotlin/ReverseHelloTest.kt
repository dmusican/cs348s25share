import kotlin.test.Test
import kotlin.test.assertEquals
import org.junit.Rule
import org.junit.contrib.java.lang.system.SystemOutRule

class ReverseHelloTest {

    @Rule @JvmField
    val systemOutRule = SystemOutRule().enableLog()

    @Test
    fun testReverseHello() {
        doReverseHello()
        var expectedOutput = ""
        for (i in 50 downTo 1) {
            expectedOutput += "Hello from thread $i${System.lineSeparator()}"
        }
        assertEquals(expectedOutput, systemOutRule.getLog())
    }
}
