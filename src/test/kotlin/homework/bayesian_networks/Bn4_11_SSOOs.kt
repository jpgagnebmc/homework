package homework.bayesian_networks

import bayesian.BN
import bayesian.BN.*
import bayesian.Node
import bayesian.Rel

class Bn4_11_SSOOs : BN {

    val S1 = Node("S1")
    val S2 = Node("S2")
    val S3 = Node("S3")
    val S4 = Node("S4")
    val O1 = Node("O1")
    val O2 = Node("O2")
    val O3 = Node("O3")
    val O4 = Node("O4")

    override val nodes: List<Node> = listOf(S1, S2, S3, S4, O1, O2, O3, O4)
    override val rels: List<Rel> = listOf(Rel(S1, S2), Rel(S2, S3), Rel(S3, S4), Rel(S1, O1), Rel(S2, O2), Rel(S3, O3), Rel(S4, O4))
}