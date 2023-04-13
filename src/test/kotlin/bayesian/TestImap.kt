package bayesian

import homework.bayesian_networks.Bn4_13_IMAP
import logic.print
import kotlin.test.assertFalse
import kotlin.test.Test
import kotlin.test.assertTrue

class TestImap {


    @Test
    fun problem() {
        Bn4_13_IMAP().apply {
            assertFalse(dsep(listOf(E), listOf(A), listOf(B)))
        }
    }

    @Test
    fun imap() {
        val E = Node("E")
        val B = Node("B")
        val R = Node("R")
        val A = Node("A")
        val C = Node("C")
        //Variable A was added with P = ∅.
        //Variable B was added with P = A, since dsep (B, A, ∅) holds and dsep (B, ∅, A) does not.
        BayesianNetwork(listOf(A, B), listOf(Rel(A, B))).apply {
            assertTrue(dsep(listOf(B), listOf(A), emptyList()))
            assertFalse(dsep(listOf(B), emptyList(), listOf(A)))
        }
        //Variable C was added with P = A, since dsep (C, A, B) holds and dsep (C, ∅, {A, B}) does not.

        BayesianNetwork(listOf(A, B, C), listOf(Rel(A, B), Rel(A, C))).apply {
            assertTrue(dsep(listOf(B), listOf(A), emptyList()))
            assertFalse(dsep(listOf(B), emptyList(), listOf(A)))
            assertTrue(dsep(listOf(C, A, B), listOf(A), emptyList()))
            assertFalse(dsep(listOf(C), emptyList(), listOf(A, B)))
        }
//
//        //Variable E was added with P = A, B since this is the smallest subset of A, B, C such
//        //that dsepG (E, P, {A, B, C} \ P) holds.
        BayesianNetwork(listOf(A, B, C, E), listOf(Rel(A, B), Rel(A, C), Rel(A, E), Rel(B, E))).apply {
            assertTrue(dsep(listOf(B), listOf(A), emptyList()))
            assertTrue(dsep(listOf(C, A, B), listOf(A), emptyList()))
            assertTrue(dsep(listOf(E), listOf(A, B), listOf(C)))
        }
        //Variable R was added with P = E since this is the smallest subset of A, B, C, E such
        //that dsepG (R, P, {A, B, C, E} \ P) holds.
        BayesianNetwork(listOf(A, B, C, E, R), listOf(Rel(A, B), Rel(A, C), Rel(A, E), Rel(B, E), Rel(E, R))).apply {
            assertTrue(dsep(listOf(B), listOf(A), emptyList()))
            assertTrue(dsep(listOf(C, A, B), listOf(A), emptyList()))
            assertTrue(dsep(listOf(E), listOf(A, B), listOf(C)))
            assertTrue(dsep(listOf(R), listOf(E), listOf(A, B, C)))
        }

        //For example, if we delete the edge E ← B, we will have dsepG (E, A, B) yet dsepG (E, A, B) does not hold in this case.
        BayesianNetwork(listOf(A, B, C, E, R), listOf(Rel(A, B), Rel(A, C), Rel(A, E), Rel(E, R))).apply {
            assertTrue(dsep(listOf(B), listOf(A), emptyList()))
            assertTrue(dsep(listOf(C, A, B), listOf(A), emptyList()))
            assertTrue(dsep(listOf(E), listOf(A, B), listOf(A, B, C)))
            assertTrue(dsep(listOf(E), listOf(A), listOf(B)))
        }
    }
}