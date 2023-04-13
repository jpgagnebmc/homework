package bayesian

import probability.I
import probability.iString

data class Markov(val node: Node, val parents: List<Node>, val nonDescendants: List<Node>) : I {
    override fun toString(): String = iString

    //    val statement: String
//        get() = "I (${node.letter}, ${parents.statement}, ${nonDescendants.statement})"
//    val List<Node>.statement: String
//        get() = when {
//            isEmpty() -> "∅"
//            size == 1 -> first().letter
//            else -> sortedBy { it.letter }.joinToString(", "){ it.letter} .braced
//        }
    val String.braced: String
        get() = "{$this}"
    override val X: List<Node>
        get() = listOf(node)
    override val Z: List<Node>
        get() = parents
    override val Y: List<Node>
        get() = nonDescendants

}