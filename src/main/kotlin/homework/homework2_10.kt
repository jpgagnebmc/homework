package homework

import formula.Formula
import formula.toCnf


fun main() {

    //Convert the following sentences into CNF:

    homework2_10_convertToCnf("2.10a","P =⇒ (Q =⇒ R)")
    homework2_10_convertToCnf("2.10b","¬((P =⇒ Q) ∧ (R =⇒ S))")
}

fun homework2_10_convertToCnf(title: String, s1: String) {
    println("$title $s1")
    val f1 = Formula.parse(s1.print()).print()
    f1.toCnf().source.print()
    println()
}
