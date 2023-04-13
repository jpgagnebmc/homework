package probability

import bayesian.BN
import bayesian.Node

data class Probability(val query: List<Node>, val given: List<Node> = emptyList()) {
    override fun toString(): String = "Pr(${query.joinToString(",")}${
        if (given.isNotEmpty()) "|${given.joinToString(",")}" else ""
    })"
}