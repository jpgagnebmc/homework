package homework

import formula.Formula
import formula.implies
import formula.world
import org.jetbrains.kotlinx.dataframe.api.*
import kotlin.test.assertFalse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Chapter2_4 {
    private val A by column<Boolean>()
    private val B by column<Boolean>()

    //For each of the following pairs of sentences, decide whether the first sentence implies the
    //second. If the implication does not hold, identify a world at which the first sentence is true but
    //the second is not.
    @Test
    fun homework2_4a() {
        Pair(Formula("(A =⇒ B) ∧ ¬B"), Formula("A")).apply {

            assertFalse(first.implies(second))

            first.world.filter { !second.evaluate(it) }.print().apply {
                assertEquals(2, rowsCount())
                assertEquals(1, filter { !A() && !B() }.rowsCount())
            }
        }
    }

    @Test
    fun homework2_4b() {
        Pair(Formula("(A ∨ ¬B) ∧ B"), Formula("A")).apply {

            assertFalse(first.implies(second))

            first.world.filter { !second.evaluate(it) }.print().apply {
                assertEquals(2, rowsCount())
                assertEquals(1, filter { !A() && !B() }.rowsCount())
            }
        }
    }

    @Test
    fun homework2_4c() {
        Pair(Formula("(A ∨ B) ∧ (A ∨ ¬B)"), Formula("A")).apply {
            assertTrue(first.implies(second))
        }
    }
}


