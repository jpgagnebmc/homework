package formula

import homework.print
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.*
public class TestImplies {

    @Test
    fun simple() {
        Formula.parse("(A =⇒ B)").print().truth
    }

    @Test
    fun truth() {
        Formula.parse("A ==> B").print().apply {
            this.print()
            vars.print()

            assertEquals(false, truth.filter { it["A"] == true && it["B"] == false }.first()["A -> B"])
            truth.print().getColumnOrNull("A -> B")!!.apply {
                assertEquals(listOf(true, false, true, true), toList())
            }
        }
    }

    @Test
    fun implies() {
        listOf(
            Pair("(A =⇒ B) ∧ (A =⇒ ¬B)", "¬A"),
//            Pair("(A =⇒ B)", "B")
            Pair("(A ∨ ¬B ∨ C) ∧ (A ∨ ¬B ∨ ¬C)", "¬A =⇒ ¬B")
        ).onEach {
            val f1 = Formula(it.first)
            val f2 = Formula(it.second)

            assertTrue(f1.implies(f2), it.toString())
        }
    }

    @Test
    fun notImplies() {
        listOf(
            Pair("A ∨ B", "A"),
        ).onEach {
            val f1 = Formula.parse(it.first)
            val f2 = Formula.parse(it.second)
            assertFalse(f1.implies(f2), it.toString())
        }
    }
}