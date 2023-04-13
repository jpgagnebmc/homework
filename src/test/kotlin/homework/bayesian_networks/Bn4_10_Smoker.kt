package homework.bayesian_networks

import bayesian.BN
import bayesian.Node
import bayesian.Rel


class Bn4_10_Smoker : BN {

    val A = Node("A")
    val S = Node("S")
    val T = Node("T")
    val C = Node("C")
    val B = Node("B")
    val P = Node("P")
    val D = Node("D")
    val X = Node("X")
    override val nodes: List<Node> = listOf(A, S, T, C, B, P, D, X)
    override val rels: List<Rel> = listOf(Rel(A, T), Rel(S, C), Rel(T, P), Rel(C, P), Rel(S, B), Rel(P, X), Rel(P, D), Rel(B, D))
}

