package homework

import formula.Formula
import formula.FormulaParser
import formula.valid
import formula.world
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

public class TestWorld {
    @Test
    fun singleVar(): Unit {
        Formula.parse("A").world.apply {
            print()
            assertEquals(2, rowsCount())
            assertEquals(1, columnsCount())
        }
    }
    @Test
    fun twoVar(): Unit {
        Formula.parse("A -> B").world.apply {
            print()
            assertEquals(4, rowsCount())
            assertEquals(2, columnsCount())
        }
    }

    @Test
    fun invalid() {
        listOf( //invalid
            "A ∧ ¬A"
        ).onEach {
            FormulaParser(it).model().apply {
                assertFalse(valid)
            }
        }
    }
}
