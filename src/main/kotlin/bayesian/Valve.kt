package bayesian

import org.jgrapht.GraphPath
import org.jgrapht.graph.DefaultEdge


fun BN.valveState(valve: Valve, z: List<Node>): Valve.State = when (valve) {
    is Valve.Convergent -> if (!z.intersect((z.map { Descendants(it) } + valve.W).toSet()).isEmpty())
        Valve.State.Closed
    else
        Valve.State.Opened

    //if ((nodes.flatMap { Descendants(it) } + valve.W).intersect(z.toSet()).isEmpty()) Valve.State.Closed else Valve.State.Opened
    is Valve.Divergent -> if (z.contains(valve.W)) Valve.State.Closed else Valve.State.Opened
    is Valve.Sequential -> if (z.contains(valve.W)) Valve.State.Closed else Valve.State.Opened
}

sealed class Valve {
    sealed class State {
        data object Opened : State()
        data object Closed : State()
    }

    abstract val rel1: Rel
    abstract val rel2: Rel
    abstract val W: Node

    data class Sequential(override val rel1: Rel, override val rel2: Rel) : Valve() {
        override val W: Node
            get() = rel1.to
    }

    data class Divergent(override val rel1: Rel, override val rel2: Rel) : Valve() {
        override val W: Node
            get() = rel1.from
    }

    data class Convergent(override val rel1: Rel, override val rel2: Rel) : Valve() {
        override val W: Node
            get() = rel2.to
    }

    companion object {
        fun from(rel1: Rel, rel2: Rel): Valve = when {
            rel1.to == rel2.to -> Convergent(rel1, rel2)
            rel1.from == rel2.from -> Divergent(rel1, rel2)
            else -> Sequential(rel1, rel2)
        }
    }

}

fun BN.valves(path: GraphPath<Node, DefaultEdge>): List<Valve> = path.vertexList.run {
    List(subList(0, size - 2).size) { i ->
        Valve.from(
            rels.firstOrNull { it.from == get(i) && it.to == get(i + 1) } ?: rels.first { it.from == get(i + 1) && it.to == get(i) },
            rels.firstOrNull { it.from == get(i + 1) && it.to == get(i + 2) } ?: rels.first { it.from == get(i + 2) && it.to == get(i + 1) }
        )
    }
}