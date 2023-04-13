package bayesian

import logic.print
import logic.printList


fun BN.dconnected(x: Node, y: Node): Boolean = !dsep(listOf(x), emptyList(), listOf(y))

fun BN.dsep(x: Node, y: Node): Boolean = dsep(listOf(x), emptyList(), listOf(y))

fun BN.dsep(X: List<Node>, Z: List<Node>, Y: List<Node>): Boolean =
    X.flatMap { nx ->
        Y.flatMap { ny -> paths(nx, ny, true) }
    }.run {
        val debug = false
        when {
            isEmpty() -> true //no paths, not connected
            else -> map { path ->
                if (debug) printList("path")
                val valves = valves(path)
                if (debug) valves.onEach {
                    println("  $it ${valveState(it, Z)}")
                }
                pathBlocked(valves, Z).also { if (debug) it.print("pathBlocked") } || blockingCollider(valves, Z)
            }.run {
                count { it } == size
            }
        }
    }

fun BN.blockingCollider(valves: List<Valve>, Z: List<Node>): Boolean = valves.filterIsInstance<Valve.Convergent>().run {
    filterNot { Z.contains(it.W) }.filterNot { Z.intersect(Descendants(it.W).toSet()).isNotEmpty() }.isNotEmpty()
}
