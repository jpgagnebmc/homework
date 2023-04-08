package homework

import formula.Formula
import formula.World
import formula.exhaustive
import formula.mutuallyExclusive
import formula.formulaMap

fun main() {

    //Which of the following pairs of sentences are mutually exclusive? Which are exhaustive? If a
    //pair of sentences is not mutually exclusive, identify a world at which they both hold. If a pair
    //of sentences is not exhaustive, identify a world at which neither holds.

    homework2_5("2.5a", "A ∨ B", "¬A ∨ ¬B")
    homework2_5("2.5b", "A ∨ B", "¬A ∧ ¬B")
    homework2_5("2.5c", "A", "(¬A ∨ B) ∧ (¬A ∨ ¬B)")
}


fun homework2_5(title: String, s1: String, s2: String) {
    println("$title $s1 and $s2")
    val f1 = Formula.parse(s1)
    val f2 = Formula.parse(s2)
    val me = Formula.mutuallyExclusive(f1, f2)
    val ex = Formula.exhaustive(f1, f2)
    println("mutually exclusive $me")
    println("exhaustive $ex")
    if (!me) {
        World.generateDf((f1.vars + f2.vars).distinct()).formulaMap {
            Triple(it, f1.evaluate(it), f2.evaluate(it))
        }.firstOrNull { it.second == true && it.third == true }
            ?.run { println("both hold world ${first}") }
    }
    if (!ex) {
        World.generateDf((f1.vars + f2.vars).distinct()).formulaMap {
            Triple(it, f1.evaluate(it), f2.evaluate(it))
        }.firstOrNull { it.second == false && it.third == false }
            ?.run { println("neither hold world ${first}") } ?: println("no worlds unheld by both ")
    }
    println()
}

