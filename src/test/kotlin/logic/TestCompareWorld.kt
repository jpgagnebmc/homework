package logic

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

public class TestCompareWorld {

    @Test
    fun mutuallyExclusive() {
        listOf(
            Pair("¬A ∧ B", "A ∧ ¬B"),
            Pair("A", "¬A")
        ).map { Pair(Formula(it.first), Formula(it.second)) }
            .onEach {
                assertTrue(Logic.mutuallyExclusive(it.first, it.second), "should be mutually exclusive ${it.first.source} ${it.second.source}")
            }
    }

    @Test
    fun notMutuallyExclusive() {
        listOf(
            Pair("A", "A"),
            Pair("A", "A ^ B")
        ).map { Pair(Formula(it.first), Formula(it.second)) }
            .onEach {
                assertFalse(Logic.mutuallyExclusive(it.first, it.second), "should not be mutually exclusive ${it.first.source} ${it.second.source}")
            }
    }

    @Test
    fun exhaustive() {
        listOf(
            Pair("A v B", "¬A v ¬B")
        ).map { Pair(Formula(it.first), Formula(it.second)) }
            .onEach {
                assertTrue(Logic.exhaustive(it.first, it.second), "should be exhaustive ${it.first.source} ${it.second.source}")
            }
    }

    @Test
    fun notExhaustive() {
        listOf(
            Pair("A", "B"),
            Pair("A ^ B", "¬A ^ ¬B")
        ).map { Pair(Formula(it.first), Formula(it.second)) }
            .onEach {
                assertFalse(Logic.exhaustive(it.first, it.second), "should not be exhaustive ${it.first.source} ${it.second.source}")
            }
    }
}