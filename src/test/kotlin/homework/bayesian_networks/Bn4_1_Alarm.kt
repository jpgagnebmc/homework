package homework.bayesian_networks

import bayesian.BN
import bayesian.Node
import bayesian.Rel

class Bn4_1_Alarm : BN {
    val E = Node("E", "Earthquake")
    val B = Node("B", "Burglary")
    val R = Node("R", "Radio")
    val A = Node("A", "Alarm")
    val C = Node("C", "Call")
    override val nodes: List<Node> = listOf(E, B, R, A, C)
    override val rels: List<Rel> = listOf(Rel(E, R), Rel(E, A), Rel(B, A), Rel(A, C))
}

