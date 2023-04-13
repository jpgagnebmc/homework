package logic

import logic.Logic.And
import logic.Logic.Not
import logic.Logic.Or
import logic.Logic.Var
import kotlin.test.Test
import kotlin.test.assertEquals

public class TestParser {
//    @Test
//    fun invalidOperator() {
//        kotlin.runCatching { Formula("A * B").print() }
//            .onSuccess { throw Exception("should fail") }
//    }

    @Test
    fun varOnly() {
        Formula("A").print().apply {
            assertEquals(1, vars.size)
            assertEquals(Var("A"), this)
        }
    }

    @Test
    fun varOnlyNot() {
        Formula("!A").print().apply {
            assertEquals(1, vars.size)
            assertEquals(Not(Var("A")), this)
        }
        Formula("¬A").print().apply {
            assertEquals(1, vars.size)
            assertEquals(Not(Var("A")), this)
        }
    }

    @Test
    fun towVarAnd() {
        Formula("A ^ B").print().apply {
            assertEquals(2, vars.size)
            assertEquals(And(Var("A"), Var("B")), this)
        }
    }

    @Test
    fun threeVarAnd() {
        Formula("A ^ B ^ C").print().apply {
            assertEquals(3, vars.size)
            assertEquals(And(Var("A"), And(Var("B"), Var("C"))), this)
        }
    }

    @Test
    fun threeVarOr() {
        Formula("A v B v C").print().apply {
            assertEquals(3, vars.size)
            assertEquals(Or(Var("A"), Or(Var("B"), Var("C"))), this)
        }
    }

    @Test
    fun threeVarAndOr() {
        Formula("A ^ B v C").print().apply {
            assertEquals(3, vars.size)
            assertEquals(And(Var("A"), Or(Var("B"), Var("C"))), this)
        }
    }

    @Test
    fun testVars() {
        assertEquals(2, Formula("¬A ∨ ¬B").print().vars.size)
        assertEquals(3, Formula("A ∨ B ∨ C").print().vars.print().size)
        assertEquals(2, Formula("¬A =⇒ ¬B").vars.size)
        assertEquals(3, Formula("(A ∨ ¬B ∨ C) ∧ (A ∨ ¬B ∨ ¬C)").vars.size)
    }

    @Test
    fun toModelAndBack(): Unit {
        listOf(
            "A",
            "!A",
            "A -> B",
            "(A ∨ B)",
            "(A -> !B)",
            "(A -> B) ∧ (A -> !B)",
            "(A ∨ B) -> (!A ∧ !B)",
        ).onEach {
            Formula(it).apply {
                assertEquals(it, source)
            }
        }
    }
}