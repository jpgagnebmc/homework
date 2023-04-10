package formula

import homework.print
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

public class TestWorld {
    @Test
    fun singleVar(): Unit {
        Formula.parse("A").world.print().apply {
            print()
            assertEquals(2, rowsCount())
            assertEquals(2, columnsCount())
        }
    }
    @Test
    fun twoVar(): Unit {
        Formula.parse("A =⇒ B").print().world.apply {
            print()
            assertEquals(4, rowsCount())
            assertEquals(3, columnsCount())
        }
    }

    @Test
    fun invalid() {
        listOf( //invalid
            "A ∧ ¬A"
        ).onEach {
            FormulaParser(it).model().apply {
                world.print()
                assertFalse(valid)
            }
        }
    }
}