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
    fun valid(): Unit {
        listOf(
            "A ∨ ¬A",
        ).onEach {
            Formula.parse(it).apply {
                println("$it valid $valid")
                assertTrue(valid, it)
            }
        }
    }

    @Test
    fun invalid(): Unit {
        listOf(
            "A",
            "!A",
            "A -> B",
            "(A ∨ B)",
            "(A -> !B)",
            "(A -> B) ∧ (A -> !B)",
            "(A ∨ B) -> (!A ∧ !B)",
        ).onEach {
            Formula.parse(it).apply {
                println("$it valid $valid")
                assertFalse(valid, it)
            }
        }
    }

}