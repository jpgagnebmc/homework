package homework

import formula.Formula
import formula.valid
import formula.world
import org.jetbrains.kotlinx.dataframe.api.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Chapter2_2 {
    private val A by column<Boolean>()
    private val B by column<Boolean>()

    // Which of the following sentences are valid? If a sentence is not valid, identify a world that
    // does not satisfy the sentence.

    @Test
    fun homework2_2a() {
        Formula("(A ∧ (A =⇒ B)) =⇒ B").apply {
            assertTrue(valid)
        }
    }

    @Test
    fun homework2_2b() {
        Formula("(A ∧ B) ∨ (A ∧ ¬B)").apply {
            assertFalse(valid)
            world.filter { !evaluate(it) }.print().apply {
                assertEquals(2, rowsCount())
                assertEquals(1, filter { !A() && !B() }.rowsCount())
            }
        }
    }

    @Test
    fun homework2_2c() {
        Formula("(A =⇒ B) =⇒ (¬B =⇒ ¬A)").apply {
            assertTrue(valid)
        }
    }
}










