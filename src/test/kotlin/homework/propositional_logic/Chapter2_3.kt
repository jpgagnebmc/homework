package homework.propositional_logic

import logic.Formula
import logic.equivalentTo
import logic.implies
import logic.print
import logic.world
import org.jetbrains.kotlinx.dataframe.api.column
import org.jetbrains.kotlinx.dataframe.api.filter
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Chapter2_3 {
    private val A by column<Boolean>()
    private val B by column<Boolean>()
    // Which of the following pairs of sentences are equivalent? If a pair of sentences is not equivalent,
    // identify a world at which they disagree (one of them holds but the other does not).

    @Test
    fun homework2_3a() {
        Pair(Formula("A =⇒ B"), Formula("B =⇒ A")).apply {
            assertFalse(first.implies(second))
            assertFalse(second.implies(first))
            assertFalse(first.equivalentTo(second))
            first.world.filter { second.evaluate(it) }.print().apply {
                assertEquals(3, rowsCount())
                assertEquals(1, filter { !A() && !B() }.rowsCount())
            }
        }
    }

    @Test
    fun homework2_3b() {
        Pair(Formula("(A =⇒ B) ∧ (A =⇒ ¬B)"), Formula("¬A")).apply {
            assertTrue(first.implies(second))
            assertTrue(second.implies(first))
            assertTrue(first.equivalentTo(second))
        }
    }

    @Test
    fun homework2_3c() {
        Pair(Formula("¬A =⇒ ¬B"), Formula("(A ∨ ¬B ∨ C) ∧ (A ∨ ¬B ∨ ¬C)")).apply {
            assertTrue(first.implies(second))
            assertTrue(second.implies(first))
            assertTrue(first.equivalentTo(second))
        }
    }

}






