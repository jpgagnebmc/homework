package logic

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

public class TestEquivalence {
    @Test
    fun equivalent() {
        listOf(
            Pair("A ∨ B", "B ∨ A"),
            Pair("A =⇒ B", "¬B =⇒ ¬A"),
            Pair("¬A", "(A =⇒ B) ∧ (A =⇒ ¬B)")
        ).onEach {
            val f1 = Logic.parse(it.first)
            val f2 = Logic.parse(it.second)
            assertTrue(f1.equivalentTo(f2), it.toString())
        }
    }

    @Test
    fun notEquivalent() {
        listOf(
            Pair("A ∨ B", "B"),
            Pair("A =⇒ B", "¬B =⇒ A"),
        ).onEach {
            val f1 = Logic.parse(it.first).print()
            val f2 = Logic.parse(it.second).print()
            assertFalse(f1.equivalentTo(f2), it.toString())
        }
    }
}