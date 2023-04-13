package homework.propositional_logic

import logic.Formula
import logic.Logic
import logic.exhaustive
import logic.mutuallyExclusive
import logic.print
import logic.world
import org.jetbrains.kotlinx.dataframe.api.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Chapter2_5 {
    private val A by column<Boolean>()
    private val B by column<Boolean>()

    //Which of the following pairs of sentences are mutually exclusive? Which are exhaustive? If a
    //pair of sentences is not mutually exclusive, identify a world at which they both hold. If a pair
    //of sentences is not exhaustive, identify a world at which neither holds.
    @Test
    fun homework2_5a() {
        Pair(Formula("A ∨ B"), Formula("¬A ∨ ¬B")).apply {

            assertFalse(Logic.mutuallyExclusive(first, second))
            assertTrue(Logic.exhaustive(first, second))

            first.world.filter { first.evaluate(it) && second.evaluate(it) }.print().apply {
                assertEquals(2, rowsCount(), "not mutually exclusive")
                assertEquals(1, filter { A() && !B() }.rowsCount())
            }

            first.world.filter { first.evaluate(it) || second.evaluate(it) }.print().apply {
                assertEquals(4, first.world.rowsCount(), "exhaustive")
            }
        }
    }

    @Test
    fun homework2_5b() {
        Pair(Formula("A ∨ B"), Formula("¬A ∧ ¬B")).apply {
            assertTrue(Logic.mutuallyExclusive(first, second))
            assertTrue(Logic.exhaustive(first, second))
        }
    }

    @Test
    fun homework2_5c() {
        Pair(Formula("A"), Formula("(¬A ∨ B) ∧ (¬A ∨ ¬B)")).apply {
            assertTrue(Logic.mutuallyExclusive(first, second))
            assertTrue(Logic.exhaustive(first, second))
        }
    }
}
