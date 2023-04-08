package homework

import formula.Formula
import formula.equivalentTo
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

public class TestEquivalence {
    @Test
    fun equivilent() {
        listOf(
            Pair("A ∨ B", "B ∨ A"),
            Pair("A =⇒ B", "¬B =⇒ ¬A"),
        ).onEach {
            val f1 = Formula.parse(it.first)
            val f2 = Formula.parse(it.second)
            assertTrue(f1.equivalentTo(f2), it.toString())
        }
    }
    @Test
    fun notEquivilent() {
        listOf(
            Pair("A ∨ B", "B"),
            Pair("A =⇒ B", "¬B =⇒ A"),
            Pair("(A =⇒ B) ∧ (A =⇒ ¬B)", "¬A")
        ).onEach {
            val f1 = Formula.parse(it.first)
            val f2 = Formula.parse(it.second)
            assertFalse(f1.equivalentTo(f2), it.toString())
        }
    }
}
