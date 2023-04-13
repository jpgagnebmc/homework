package probability

import bayesian.Markov
import bayesian.Node
import logic.braced

interface I {
    val X: List<Node>
    val Z: List<Node>
    val Y: List<Node>
}

fun I(X: List<Node>, Z: List<Node>, Y: List<Node>): I = IImpl(X, Z, Y)

val I.iString: String
    get() = "I(${X.s}, ${Z.s}, ${Y.s})"

private val List<Node>.s: String
    get() = when {
        size > 1 -> sortedBy { it.letter }.joinToString(", ") { it.letter }.braced
        size == 1 -> first().letter
        else -> "∅"
    }

data class IImpl(override val X: List<Node>, override val Z: List<Node>, override val Y: List<Node>) : I {
    override fun toString(): String = iString

}

fun List<I>.conditionallyIndependent(I: I): Boolean = I.Z.none { z -> allParents(I.X).contains(z) }
fun List<I>.allParents(nodes: List<Node>): List<Node> = nodes.flatMap { allParents(it) }
fun List<I>.allParents(node: Node): List<Node> = filter { it.X.contains(node) }.flatMap { it.Z + it.Z.flatMap { allParents(it) } }
fun List<I>.marginallyIndependent(I: I): Boolean = I.Z.none { z -> any { it.X.contains(z) } }


fun Probability.removeIndependencies(markov: List<Markov>) = copy(
    given = given.filterNot { g ->
        g in query.flatMap { n -> markov.first { it.node == n }.nonDescendants }
    }
)

