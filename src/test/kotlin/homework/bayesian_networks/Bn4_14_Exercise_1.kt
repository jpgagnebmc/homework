package homework.bayesian_networks

import bayesian.BN
import bayesian.Node
import bayesian.Rel

import dataframe.Pr
import org.jetbrains.kotlinx.dataframe.*

import org.jetbrains.kotlinx.dataframe.api.*

class Bn4_14_Exercise_1 : BN {

    val A = Node("A")
    val B = Node("B")
    val C = Node("C")
    val D = Node("D")
    val E = Node("E")
    val F = Node("F")
    val G = Node("G")
    val H = Node("H")

    val colA by column<Int>("A")
    val colB by column<Int>("B")
    val colC by column<Int>("C")
    val colD by column<Int>("D")
    val colE by column<Int>("E")
    val colF by column<Int>("F")
    val colG by column<Int>("G")
    val colH by column<Int>("H")

    override val nodes: List<Node> = listOf(A, B, C, D, E, F, G, H)
    override val rels: List<Rel> = listOf(Rel(A, C), Rel(A, D), Rel(B, D), Rel(B, E), Rel(C, F), Rel(D, F), Rel(E, H), Rel(F, G), Rel(F, H))

    val cpts: Map<String, AnyFrame> = mapOf(
        "Pr(A)" to dataFrameOf("A", Pr.name()).invoke(1, 0.2, 0, 0.8),
        "Pr(B)" to dataFrameOf("B", Pr.name()).invoke(1, 0.7, 0, 0.3),
        "Pr(E|B)" to dataFrameOf("B", "E", Pr.name()).invoke(1, 1, 0.1, 1, 0, 0.9, 0, 1, 0.9, 0, 0, 0.1),
        "Pr(D|A,B)" to dataFrameOf("A", "B", "D", Pr.name()).invoke(
            1, 1, 1, 0.5,
            1, 1, 0, 0.5,
            1, 0, 1, 0.6,
            1, 0, 0, 0.4,
            0, 1, 1, 0.1,
            0, 1, 0, 0.9,
            0, 0, 1, 0.8,
            0, 0, 0, 0.2
        ),
    )


}