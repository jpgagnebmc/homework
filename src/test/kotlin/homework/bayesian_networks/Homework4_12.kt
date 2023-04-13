package homework.bayesian_networks

import bayesian.BN
import bayesian.BayesianNetwork
import bayesian.Node
import bayesian.Rel
import bayesian.dsep
import bayesian.list
import logic.print
import logic.printList
import kotlin.random.Random
import kotlin.test.Test

class Homework4_12 {
    @Test
    fun homework4_2() {
        val A = Node("A")
        val B = Node("B")
        val C = Node("C")
        val D = Node("D")
        val nodes = listOf(A, B, C, D)
//


        val possibleRels = nodes.flatMap { a ->
            nodes.flatMap { b ->
                if (b == a) emptyList() else listOf(Rel(a, b), Rel(b, a))
            }
        }.distinct().print()


        val bn1 = BayesianNetwork(listOf(A, B, C, D), randomRels(possibleRels)).print()
        val bn2 = BayesianNetwork(listOf(A, B, C, D), randomRels(possibleRels)).print()
//
//        (1..10000).flatMap {
//            nodes.flatMap { a ->
//                nodes.map { b ->
//                    var r: Pair<Pair<BN, BN>, Boolean>? = null
//                    kotlin.runCatching {
//                        val bns = Pair(bn1, bn2)
//                        r = Pair(
//                            bns,
//                            bn1.dsep(a, b) && bn2.dsep(a, b),
//                        )
//                    }
//                    r
//                }
//            }
//        }.filterNotNull().filter { it.second}.printList()


        //      randomRel(nodes)


    }
}

fun randomRels(possibleRels: List<Rel>): List<Rel> = mutableListOf<Rel>().apply {
    val remaining = possibleRels.toMutableList()
    (1..4).onEach {
        val r = remaining[Random.nextInt(remaining.size)]
        remaining.remove(r)
        add(r)
    }
}