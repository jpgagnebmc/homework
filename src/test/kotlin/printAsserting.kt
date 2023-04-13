import logic.print
import kotlin.test.assertEquals

fun <T> T.printAsserting(expected: T, message: String? = null): T = apply {
    this.print(message)
    assertEquals(expected, this)
}

