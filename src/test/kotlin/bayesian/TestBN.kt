package bayesian


import homework.bayesian_networks.Bn4_1_Alarm
import logic.printList
import printAsserting
import probability.Probability
import probability.chainRule
import probability.removeIndependencies
import kotlin.test.Test
import kotlin.test.assertEquals


class TestBN {


    @Test
    fun parents() {
        Bn4_1_Alarm().apply {
            assertEquals(listOf(E, B), NetworkParents)
            assertEquals(listOf(E, B), Parents(A))
        }
    }

    @Test
    fun descendants() {
        Bn4_1_Alarm().apply {
            assertEquals(listOf(A, C), Descendants(B))
            assertEquals(listOf(C), Descendants(A))
            assertEquals(listOf(R, A, C), Descendants(E))
            assertEquals(emptyList(), Descendants(R))
        }
    }

    @Test
    fun nonDescendants() {
        Bn4_1_Alarm().apply {
            assertEquals(listOf(A, C), Descendants(B))
            assertEquals(listOf(), Parents(B))
            assertEquals(listOf(E, R), Non_Descendants(B))
        }
    }

    @Test
    fun markov() {
        Bn4_1_Alarm().apply {
            Markov.apply {
                assertEquals("I(C, A, {B, E, R})", first { it.X.contains(C) }.toString())
                assertEquals("I(R, E, {A, B, C})", first { it.X.contains(R) }.toString())
                assertEquals("I(A, {B, E}, R)", first { it.X.contains(A) }.toString())
                assertEquals("I(B, ∅, {E, R})", first { it.X.contains(B) }.toString())
                assertEquals("I(E, ∅, B)", first { it.X.contains(E) }.toString())
            }
        }
    }

    @Test
    fun testChainRule() {
        Bn4_1_Alarm().apply {
            Probability(listOf(R, C, A, E, B)).chainRule().printList().apply {
                assertEquals(5, size)
                assertEquals("[Pr(R|C,A,E,B), Pr(C|A,E,B), Pr(A|E,B), Pr(E|B), Pr(B)]", toString())
            }
        }
    }

    @Test
    fun testIndependance() {
        Bn4_1_Alarm().apply {
            Probability(listOf(C), listOf(A, R, B, E)).removeIndependencies(Markov).printAsserting(Probability(listOf(C), listOf(A)))
            Probability(listOf(A), listOf(R, B, E)).removeIndependencies(Markov).printAsserting(Probability(listOf(A), listOf(B, E)))
            Probability(listOf(R), listOf(B, E)).removeIndependencies(Markov).printAsserting(Probability(listOf(R), listOf(E)))
            Probability(listOf(B), listOf(E)).removeIndependencies(Markov).printAsserting(Probability(listOf(B)))
        }
    }

    @Test
    fun parameters() {
        Bn4_1_Alarm().apply {
            Markov.printList()
            assertEquals(Probability(listOf(C, A, R, E, B)), parameterStatement)
            chainedIndependantParameterStatements.printList().apply {
                assertEquals(5, size)
                assertEquals("[Pr(C|A), Pr(A|E,B), Pr(R|E), Pr(E), Pr(B)]", toString())
            }
        }
    }
}
