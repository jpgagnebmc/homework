package logic

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

public class TestValidity {
    @Test
    fun invalidOperator() {
        Logic.parse("A=>B").print()
    }

    @Test
    fun valid(): Unit {
        listOf(
            "A ∨ ¬A",
        ).onEach {
            Logic.parse(it).apply {
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
            Logic.parse(it).apply {
                println("$it valid $valid")
                assertFalse(valid, it)
            }
        }
    }

}