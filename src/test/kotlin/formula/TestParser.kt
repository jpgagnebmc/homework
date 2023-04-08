package formula

import homework.print
import kotlin.test.Test
import kotlin.test.assertFalse

public class TestParser {
    @Test
    fun invalidOperator() {
        Formula.parse("A=>B").print()
    }

    @Test
    fun valid(): Unit {
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
                println("$it valid $valid")
//                assertTrue(valid, it)
//                assertEquals(it, source)
            }
        }
    }
    @Test
    fun invalid() {
        listOf( //invalid
            "A ∧ ¬A"
        ).onEach {
            FormulaParser(it).model().apply {
                assertFalse(valid)
            }
        }
    }
}