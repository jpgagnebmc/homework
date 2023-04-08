package formula

import homework.print
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

public class TestValidity {
    @Test
    fun invalidOperator() {
        Formula.parse("A=>B").print()
    }

    @Test
    fun invalid(): Unit {
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
                assertFalse(valid, it)
            }
        }
    }

}