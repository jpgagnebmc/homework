package homework

import formula.Formula
import formula.implies
import formula.world
import formula.formulaMap

fun main() {

    //For each of the following pairs of sentences, decide whether the first sentence implies the
    //second. If the implication does not hold, identify a world at which the first sentence is true but
    //the second is not.

    homeworkImplies("2.4a", "(A =⇒ B) ∧ ¬B", "A")
    homeworkImplies("2.4b", "(A ∨ ¬B) ∧ B", "A")
    homeworkImplies("2.4c", "(A ∨ B) ∧ (A ∨ ¬B)", "A")
}

private fun homeworkImplies(title: String, s1: String, s2: String) {
    val f1 = Formula.parse(s1)
    val f2 = Formula.parse(s2)
    val result = f1.implies(f2)
    println("$title is $s1 implies $s2 $result")

    if(!result) {
        println("world true for first sentence but not for second: ")
        f1.world.formulaMap {
            if (f1.evaluate(it) == true && f2.evaluate(it) == false) it else null
        }.filterNotNull()
            .firstOrNull()?.print() ?: println("none")
    }
    println()
}



