package homework.bayesian_networks

import bayesian.BN
import bayesian.BayesianNetwork
import bayesian.Node
import bayesian.Rel

import bayesian.dsep
import com.ibm.icu.lang.UCharacter.GraphemeClusterBreak.V
import logic.print
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

data class Bn4_13_IMAP(
    val E: Node = Node("E"),
    val B: Node = Node("B"),
    val R: Node = Node("R"),
    val A: Node = Node("A"),
    val C: Node = Node("C"),
    override val nodes: List<Node> = listOf(E, B, R, A, C),
    override val rels: List<Rel> = listOf(Rel(E, R), Rel(A, E), Rel(B, E), Rel(A, B), Rel(A, C))
) : BN
