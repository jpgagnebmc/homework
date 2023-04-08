package homework

import formula.Formula
import formula.filter
import formula.implies
import formula.truth
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

public class TestImplies {

    @Test
    fun truth() {
        Formula.parse("A => B").print().apply {

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
        ).onEach {
            val f1 = Formula.parse(it.first)
            val f2 = Formula.parse(it.second)
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
