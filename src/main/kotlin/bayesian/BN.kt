package bayesian

import probability.Probability
import probability.chainRule
import probability.removeIndependencies


interface Node {
    val letter: String
    val description: String
}

fun Node(letter: String, description: String = ""): Node = NodeImpl(letter, description)

interface BN {
    val nodes: List<Node>
    val rels: List<Rel>


    val parentMap: Map<Node, List<Rel>>
        get() = nodes.associateWith { node -> rels.filter { it.to == node } }

    val descendantMap: Map<Node, List<Rel>>
        get() = nodes.associateWith { node -> rels.filter { it.from == node } }

    fun Descendants(node: Node): List<Node> = rels.filter { it.from == node }.map { it.to }
        .run { this + flatMap { Descendants(it) } }

    fun Non_Descendants(node: Node): List<Node> = nodes.subtract((Descendants(node) + Parents(node) + node).toSet()).toList()

    fun Parents(node: Node): List<Node> = rels.filter { it.to == node }.map { it.from }
    //.run { this + flatMap { descendants(it) } }  not recursive like descendants?

    fun children(node: Node): List<Node> = rels.filter { it.from == node }.map { it.to }

    fun connections(node: Node): List<Node> = Parents(node) + children(node)

    fun parameters(node: Node): String {
        val parents = Parents(node)
        val parentStatement = if (parents.isNotEmpty()) {
            "|${parents.joinToString(",") { it.letter }}"
        } else ""
        return "Pr(${node.letter}$parentStatement)"
    }
    val NetworkParents: List<Node>
        get() = parentMap.filter { it.value.isEmpty() }.map { it.key }

    val Markov: List<Markov>
        get() = nodes.map { Markov(it, Parents(it), Non_Descendants(it)) }

    val chainedIndependantParameterStatements: List<Probability>
        get() = parameterStatement.chainRule().map { it.removeIndependencies(Markov) }

    val parameterStatement: Probability
        get() = Probability(nodesSortedByParent())
}


fun BayesianNetwork(nodes: List<Node>, rels: List<Rel>): BN = BNImpl(nodes, rels)

data class BNImpl(override val nodes: List<Node>, override val rels: List<Rel>) : BN

fun BN.nodesSortedByParent(): List<Node> = nodes.toMutableList().apply {
    var finished = false
    var limit = 1000
    while (!finished && limit > 0) {
        finished = true
        nodes.onEach { node ->
            Parents(node).onEach { parent ->
                if (indexOf(node) > indexOf(parent)) {
                    remove(node)
                    add(0, node)
                    finished = false
                }
            }
        }
        limit -= 1
        if (limit == 0) throw Exception("nodesSortedByParent exceeded limit")
    }
//    println("limit remaining $limit")
}

val Node.list: List<Node>
    get() = listOf(this)
