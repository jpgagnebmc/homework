package formula

import kotlin.test.Test
import kotlin.test.assertEquals

public class TestToCnf {
    @Test
    fun toCnf(): Unit {

        listOf(
            Pair("¬¬A", "A"),
            Pair("¬(A ∨ B)", "¬A ∧ ¬B"),
            Pair("A =⇒ B", "¬A ∨ B"),
            Pair("¬(A ∨ B)", "¬A ∧ ¬B"),
            Pair("¬(A ∧ B)", "¬A ∨ ¬B"),
            Pair("A ∨ (B ∧ C)", "(A ∨ B) ∧ (A ∨ C)"),
//            Pair("¬(A ∨ B) ∨ C", ""),
//            Pair("(!A ∧ !B) ∨ C", "")
        ).onEach {
            Formula.parse(it.first).toCnf().apply {
                assertEquals(it.second.noSpecialCharacters, source)
            }
        }


    }

    private val String.noSpecialCharacters: String
        get() = replace("¬", "!")//.replace("∨", "v")

}