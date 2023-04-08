package formula

import homework.print
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

public class TestParser {
    @Test
    fun invalidOperator() {
        Formula.parse("A=>B").print()
    }

    @Test
    fun toModelAndBack(): Unit {
        listOf(
            "A", //TODO NOT valid
            "!A", //TODO NOT valid
            "A -> B",
            "(A ∨ B)",
            "(A -> !B)",
            "(A -> B) ∧ (A -> !B)",
            "(A ∨ B) -> (!A ∧ !B)",
        ).onEach {
            FormulaParser(it).model().apply {
                assertEquals(it, source)
            }
        }
    }

}