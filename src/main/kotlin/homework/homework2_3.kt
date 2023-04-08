package homework

import formula.Formula
import formula.equivalentTo
import formula.implies


fun main() {

    //Which of the following pairs of sentences are equivalent? If a pair of sentences is not equiv-
    //alent, identify a world at which they disagree (one of them holds but the other does not).

    homeworkEquivalence("2.3a", "A =⇒ B", "B =⇒ A")
    homeworkEquivalence("2.3b", "(A =⇒ B) ∧ (A =⇒ ¬B)", "¬A")
    homeworkEquivalence("2.3c", "¬A =⇒ ¬B", "(A ∨ ¬B ∨ C) ∧ (A ∨ ¬B ∨ ¬C)")

}


private fun homeworkEquivalence(title: String, s1: String, s2: String) {
    val f1 = Formula.parse(s1)
    val f2 = Formula.parse(s2)

    val result = f1.equivalentTo(f2)
    println("$title $result")
    println("$s1 implies $s2 ${f1.implies(f2)}")
    println("$s2 implies $s1 ${f2.implies(f1)}")
    println()
//    if (!result) {
//        World.generate(((f1.vars + f2.vars).distinct())).run {
//            df.onEach {
//                if (f1.evaluate(it) != f2.evaluate(it)) println(it)
//            }
//        }
//    }
}






