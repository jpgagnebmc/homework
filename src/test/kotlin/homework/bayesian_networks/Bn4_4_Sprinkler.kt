package homework.bayesian_networks

import bayesian.BN
import dataframe.Pr
import logic.print
import bayesian.Rel
import bayesian.Node

import kotlin.test.assertEquals
import org.jetbrains.kotlinx.dataframe.*
import org.jetbrains.kotlinx.dataframe.api.*


class Bn4_4_Sprinkler : BN {
    val A = Node("A", "Winter?")
    val B = Node("B", "Sprinkler?")
    val C = Node("C", "Rain?")
    val D = Node("D", "Wet Grass?")
    val E = Node("E", "Slippery Road?")
    override val nodes: List<Node> = listOf(A, B, C, D, E)
    override val rels: List<Rel> = listOf(Rel(A, B), Rel(A, C), Rel(B, D), Rel(C, D), Rel(C, E))
    val colA by column<Boolean>("A")
    val colB by column<Boolean>("B")
    val colC by column<Boolean>("C")
    val colD by column<Boolean>("D")
    val colE by column<Boolean>("E")

    val cpts: Map<String, AnyFrame> = mapOf(
        "Pr(A)" to dataFrameOf("A", Pr.name()).invoke(true, 0.6, false, 0.4),
        "Pr(B|A)" to dataFrameOf("A", "B", Pr.name()).invoke(true, true, 0.2, true, false, 0.8, false, true, 0.75, false, false, 0.25),
        "Pr(C|A)" to dataFrameOf("A", "C", Pr.name()).invoke(true, true, 0.8, true, false, 0.2, false, true, 0.1, false, false, 0.9),
        "Pr(D|B,C)" to dataFrameOf("B", "C", "D", Pr.name()).invoke(true, true, true, 0.95, true, false, false, 0.05, true, false, true, 0.9, true, false, false, 0.1, false, true, true, 0.8, false, true, false, 0.2, false, false, true, 0, false, false, false, 1),
        "Pr(E|C)" to dataFrameOf("E", "C", Pr.name()).invoke(true, true, 0.7, true, false, 0.3, false, true, 0.0, false, false, 1),
    )
}



