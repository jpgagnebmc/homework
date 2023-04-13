package bayesian

public data class NodeImpl(override val letter: String, override val description: String) : Node {
    override fun toString(): String = letter
}

val List<Node>.letters: List<String>
    get() = map { it.letter }
val Node.outgoing: NodeDirection
    get() = NodeDirection(this, true)
val Node.inbound: NodeDirection
    get() = NodeDirection(this, false)

data class NodeDirection(val node: Node, val outgoing: Boolean) {
    override fun equals(other: Any?): Boolean {
        other as NodeDirection

        return node == other.node && outgoing == other.outgoing
    }

    override fun hashCode(): Int = node.hashCode() + outgoing.hashCode()
}

interface NetworkNode {
    val network: BN
    val node: Node
}

data class NetworkNodeImpl(override val network: BN, override val node: Node) : NetworkNode, Node by node

fun NetworkNode(network: BN, node: Node): NetworkNode = NetworkNodeImpl(network, node)
