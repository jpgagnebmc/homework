package bayesian

import homework.bayesian_networks.Bn4_13_IMAP
import homework.bayesian_networks.Bn4_14_Exercise_1
import homework.bayesian_networks.Bn4_1_Alarm
import logic.print
import logic.printList
import probability.Probability
import probability.chainRule
import probability.conditionallyIndependent
import probability.marginallyIndependent
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class TestIndependence {

    @Test
    fun chainRuleOrdering() {
        Bn4_14_Exercise_1().apply {
            assertEquals(listOf(A, B, C, D, E, F, G, H), nodes)
            assertEquals(listOf(H, G, F, E, D, C, A, B), nodesSortedByParent())
            assertEquals(Probability(listOf(H, G, F, E, D, C, A, B)), parameterStatement)
            assertEquals("[Pr(H|G,F,E,D,C,A,B), Pr(G|F,E,D,C,A,B), Pr(F|E,D,C,A,B), Pr(E|D,C,A,B), Pr(D|C,A,B), Pr(C|A,B), Pr(A|B), Pr(B)]", parameterStatement.chainRule().toString())
        }
        Bn4_1_Alarm().apply {
            assertEquals(listOf(E, B, R, A, C), nodes)
            assertEquals(listOf(C, A, R, E, B), nodesSortedByParent())
            assertEquals(Probability(listOf(C, A, R, E, B)), parameterStatement)
            assertEquals("[Pr(C|A,R,E,B), Pr(A|R,E,B), Pr(R|E,B), Pr(E|B), Pr(B)]", parameterStatement.chainRule().toString())
        }
    }

    @Test
    fun HiddenMarkovModel() {
        Bn4_1_Alarm().apply {
//            markov.printList()
            this::class.simpleName.print()
//I(E, ∅, B) marginallyIndependent true
//I(B, ∅, {E,R}) marginallyIndependent true
//I(R, E, {A,B,C}) marginallyIndependent false
//I(A, {B,E}, R) marginallyIndependent false
//I(C, A, {B,E,R}) marginallyIndependent false
//Note that variables B and E have no parents, hence, they are marginally independent of
//their nondescendants.
            assertTrue(Markov.marginallyIndependent(Markov.first { it.X.contains(E) }))
            assertTrue(Markov.marginallyIndependent(Markov.first { it.X.contains(B) }))
            assertFalse(Markov.marginallyIndependent(Markov.first { it.X.contains(R) }))
            assertFalse(Markov.marginallyIndependent(Markov.first { it.X.contains(A) }))
            assertFalse(Markov.marginallyIndependent(Markov.first { it.X.contains(C) }))
            Markov.printList().onEach {
//                println("$it marginallyIndependent ${marginallyIndependent(it)}")
                println("$it conditionallyIndependent ${Markov.conditionallyIndependent(it)}")
            }

        }

        Bn4_13_IMAP().apply {
            this::class.simpleName.print()
//I(E, {A,B}, C) marginallyIndependent false
//I(B, A, C) marginallyIndependent true
//I(R, E, {A,B,C}) marginallyIndependent false
//I(A, ∅, ∅) marginallyIndependent true
//I(C, A, {B,E,R}) marginallyIndependent true

//I(E, {A,B}, C) conditionallyIndependent false
//I(B, A, C) conditionallyIndependent false
//I(R, E, {A,B,C}) conditionallyIndependent false
//I(A, ∅, ∅) conditionallyIndependent true
//I(C, A, {B,E,R}) conditionallyIndependent false
            Markov.printList().onEach {
//                println("$it marginallyIndependent ${marginallyIndependent(it)}")
                println("$it conditionallyIndependent ${Markov.conditionallyIndependent(it)}")
            }
            assertFalse(Markov.marginallyIndependent(Markov.first { it.X.contains(E) }))
            assertFalse(Markov.marginallyIndependent(Markov.first { it.X.contains(B) }))
            assertFalse(Markov.marginallyIndependent(Markov.first { it.X.contains(R) }))
            assertTrue(Markov.marginallyIndependent(Markov.first { it.X.contains(A) }))
            assertFalse(Markov.marginallyIndependent(Markov.first { it.X.contains(C) }))

        }
    }

    @Test
    fun testIMarginallyIndependent() {
        val A = Node("A")
        val B = Node("B")
        val C = Node("C")
        BayesianNetwork(listOf(A, B, C), listOf()).apply {
            Markov.print()
            assertTrue(Markov.marginallyIndependent(Markov.first { it.X.contains(A) }))
            assertTrue(Markov.marginallyIndependent(Markov.first { it.X.contains(B) }))
            assertTrue(Markov.marginallyIndependent(Markov.first { it.X.contains(C) }))
        }
        BayesianNetwork(listOf(A, B, C), listOf(Rel(B, C))).apply {
            Markov.printList()
            assertTrue(Markov.marginallyIndependent(Markov.first { it.X.contains(A) }))
            assertTrue(Markov.marginallyIndependent(Markov.first { it.X.contains(B) }))
            assertFalse(Markov.marginallyIndependent(Markov.first { it.X.contains(C) }))
        }
    }

    @Test
    fun testIConditionallyIndependent() {
        val A = Node("A")
        val B = Node("B")
        val C = Node("C")
        BayesianNetwork(listOf(A, B, C), listOf()).apply {
            Markov.print()
            assertTrue(Markov.conditionallyIndependent(Markov.first { it.X.contains(A) }))
            assertTrue(Markov.conditionallyIndependent(Markov.first { it.X.contains(B) }))
            assertTrue(Markov.conditionallyIndependent(Markov.first { it.X.contains(C) }))
        }
        BayesianNetwork(listOf(A, B, C), listOf(Rel(A, B), Rel(B, C))).apply {
            Markov.printList()
            assertTrue(Markov.conditionallyIndependent(Markov.first { it.X.contains(A) }.print()))
            assertFalse(Markov.conditionallyIndependent(Markov.first { it.X.contains(B) }))
            assertFalse(Markov.conditionallyIndependent(Markov.first { it.X.contains(C) }.print()))
        }
    }
}
