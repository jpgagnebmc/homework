package bayesian

import bayesian.BN.*
import org.jgrapht.Graph
import org.jgrapht.GraphPath
import org.jgrapht.alg.shortestpath.AllDirectedPaths
import org.jgrapht.graph.DefaultDirectedGraph
import org.jgrapht.graph.DefaultEdge

fun BN.paths(n1: Node, n2: Node, undirected: Boolean = true): List<GraphPath<Node, DefaultEdge>> {
    val graph2: Graph<Node, DefaultEdge> = DefaultDirectedGraph(DefaultEdge::class.java)
    graph2.apply {
        nodes.onEach { addVertex(it) }
        rels.onEach {
            addEdge(it.from, it.to)
            if (undirected) addEdge(it.to, it.from)
        }
    }
    return AllDirectedPaths(graph2).getAllPaths(n1, n2, true, nodes.size)
}

fun BN.pathBlocked(valves: List<Valve>, z: List<Node>): Boolean {
    return when {
        valves.isEmpty() -> false
        else -> valves.map { valveState(it, z) }.filterIsInstance<Valve.State.Closed>().isNotEmpty()
    }
}

