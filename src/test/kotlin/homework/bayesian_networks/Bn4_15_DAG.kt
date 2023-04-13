package homework.bayesian_networks

import bayesian.BN
import bayesian.Node
import bayesian.Rel

class Bn4_15_DAG : BN {

    val A1 = Node("A1")
    val A2 = Node("A2")
    val A3 = Node("A3")
    val B1 = Node("B1")
    val B2 = Node("B2")
    val B3 = Node("B3")
    val C1 = Node("C1")
    val C2 = Node("C2")
    val C3 = Node("C3")

    val A: List<Node> = listOf(A1, A2, A3)
    val B: List<Node> = listOf(B1, B2, B3)
    val C: List<Node> = listOf(C1, C2, C2)

    override val nodes: List<Node> = listOf(A1, B1, C1, A2, B2, C2, A3, B3, C3)
    override val rels: List<Rel> = listOf(
        Rel(A1, A2), Rel(A1, B2), Rel(A2, A3), Rel(A2, B3), Rel(B1, B2), Rel(B1, C2), Rel(B2, B3), Rel(B2, C3), Rel(C1, C2), Rel(C2, C3)
    )
}