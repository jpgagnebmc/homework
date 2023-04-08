package homework

import formula.Formula
import formula.FormulaParser
import formula.valid
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

public class TestParser {
    @Test
    fun invalidOperator() {
        Formula.parse("A=>B").print()
    }

    @Test
    fun valid(): Unit {
        listOf(
            //valid
            "A",
            "!A",
            "A -> B",
            "(A ∨ B)",
            "(A -> !B)",
            "(A -> B) ∧ (A -> !B)",
            "(A ∨ B) -> (!A ∧ !B)",
        ).onEach {
            FormulaParser(it).model().apply {
//                assertTrue(valid, it)
                assertEquals(it, source)
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
