package bayesian


import homework.bayesian_networks.Bn4_10_Smoker
import homework.bayesian_networks.Bn4_11_SSOOs
import homework.bayesian_networks.Bn4_1_Alarm
import logic.print
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TestDsep {
    val X = Node("X")
    val R = Node("R")
    val T = Node("T")
    val U = Node("U")
    val V = Node("V")
    val Y = Node("Y")
    val S = Node("S")

    @Test
    fun tutorialRule1And2() { //http://bayes.cs.ucla.edu/BOOK-2K/d-sep.html

        BayesianNetwork(listOf(X, R, S, T, U, V, Y), listOf(Rel(X, R), Rel(R, S), Rel(S, T), Rel(U, T), Rel(V, U), Rel(V, Y))).apply {
            assertFalse(dsep(X, R), "X, R")
            assertTrue(dsep(X, Y), "X, Y")
            assertTrue(dsep(X, V), "X, V")
            assertTrue(dsep(S, U), "S, U")
            assertTrue(dsep(R, U), "R, U")
            assertFalse(dsep(X, T), "X, T")
            assertFalse(dsep(T, Y), "T, Y")
            assertFalse(dsep(U, Y), "U, Y")
            assertFalse(dsep(T, V), "T, V")
            assertFalse(dsep(T, U), "T, U")
            assertFalse(dsep(X, S), "X, S")

            assertTrue(dsep(listOf(X), listOf(R, V), listOf(Y)))
            assertTrue(dsep(listOf(X), listOf(R, V), listOf(S)))
            assertTrue(dsep(listOf(U), listOf(R, V), listOf(Y)))
            assertTrue(dsep(listOf(S), listOf(R, V), listOf(U)))
            assertFalse(dsep(listOf(S), listOf(R, V), listOf(T)))
            assertFalse(dsep(listOf(U), listOf(R, V), listOf(T)))

            assertTrue(dsep(listOf(S), listOf(T), listOf(U)))
        }
    }

    @Test
    fun tutorialRule3() {
        val W = Node("W")
        val P = Node("P")
        val Q = Node("Q")
        BayesianNetwork(
            listOf(X, R, S, T, U, V, Y, W, P, Q),
            listOf(Rel(X, R), Rel(R, S), Rel(S, T), Rel(U, T), Rel(V, U), Rel(V, Y), Rel(R, W), Rel(T, P), Rel(V, Q)),
        ).apply {
            Descendants(R).print("Decendants R")
            Descendants(P).print("Decendants P")

            assertFalse(dsep(listOf(S), listOf(R, P), listOf(Y)))
            assertTrue(dsep(listOf(X), listOf(R, P), listOf(U)))
        }
    }

    @Test
    fun alarm() {
        Bn4_1_Alarm().apply {
//            assertFalse(dsep2(listOf(A), emptyList(), listOf(C)))
//            assertFalse(dsep2(listOf(A), listOf(R), listOf(C)))

            //A convergent valve (→W←) is closed iff neither variable W nor any of its descendant appears in Z.

            assertTrue { valveState(Valve.from(Rel(E, R), Rel(E, A)), listOf(E, C)) is Valve.State.Closed }
            assertTrue { valveState(Valve.from(Rel(E, A), Rel(B, A)), listOf(E, C)) is Valve.State.Opened }

            assertTrue(dsep(listOf(R), listOf(E, C), listOf(B)))
            assertFalse(dsep(listOf(R), listOf(), listOf(C)))
//            assertTrue(dsep2(listOf(Bn4_1.Nodes.A), listOf(Bn4_1.Nodes.B), listOf(Bn4_1.Nodes.C)))

            //Note that according to this definition, a path with no valves (i.e., X → Y ) is never blocked.
            assertFalse(dsep(listOf(A), listOf(), listOf(C)))

            //Considering the DAG G on
            //the left of this figure, R and B are d-separated by E and C: dsepG (R, {E, C}, B). There
            //is only one path connecting R and B in this DAG and it has two valves: R←E→A and
            //E→A←B. The first valve is closed given E and C and the second valve is open given
            //E and C. But the closure of only one valve is sufficient to block the path, therefore
            //establishing d-separation.
            assertTrue(dsep(listOf(R), listOf(E, C), listOf(B)))

            //consider the DAG G on the right of
            //Figure 4.9 in which R and C are not d-separated: dsepG (R, ∅, C) does not hold. Again,
            //there is only one path in this DAG between R and C and it contains two valves, R←E→A
            //and E→A→C, which are both open. Hence, the path is not blocked and d-separation
            //does not hold.

            assertTrue(dsep(listOf(R), listOf(), listOf(B)))
            assertFalse(dsep(listOf(R), listOf(), listOf(C)))
        }
    }

    @Test
    fun smoker() {
        //Consider now the DAG G in Figure 4.10 where our goal here is to test whether B and
        //C are d-separated by S: dsepG (B, S, C). There are two paths between B and C in this
        //DAG. The first path has only one valve, C←S→B, which is closed given S and, hence,
        //the path is blocked. The second path has two valves, C→P→D and P →D←B, where
        //the second valve is closed given S and, hence, the path is blocked. Since both paths are
        //blocked by S, we then have that C and B are d-separated by S
        Bn4_10_Smoker().apply {
            assertTrue(dsep(listOf(B), listOf(S), listOf(C)))
        }
    }

    @Test
    fun bboo() {
        Bn4_11_SSOOs().apply {
            assertTrue(dsep(listOf(S1), listOf(S2), listOf(S3, S4)))
        }
    }
}


