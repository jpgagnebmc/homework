package bayesian


interface Rel {
    val from: Node
    val to: Node
}

fun Rel(from: Node, to: Node): Rel = RelImpl(from, to)
data class RelImpl(override val from: Node, override val to: Node) : Rel {
    override fun toString(): String = "Rel(${from.letter},${to.letter})"
}