package homework

import formula.Formula
import formula.world

import kotlin.test.Test
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Chapter2_1() {
    private val A by column<Boolean>()
    private val B by column<Boolean>()

    //Show that the following sentences are consistent by identifying a world that satisfies each sentence:
    @Test
    fun homework2_1a(): Unit {
        Formula("(A =⇒ B) ∧ (A =⇒ ¬B)").apply {
            world.filter { evaluate(it) }.print().apply {
                assertEquals(2, rowsCount())
                assertEquals(1, filter { !A() && !B() }.rowsCount())
            }
        }
    }

    @Test
    fun homework2_1b() {
        Formula("(A ∨ B) =⇒ (¬A ∧ ¬B)").apply {
            world.filter { evaluate(it) }.print().apply {
                assertEquals(1, rowsCount())
                assertEquals(1, filter { !A() && !B() }.rowsCount())
            }
        }
    }
}





