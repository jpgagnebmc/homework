package homework

import formula.Formula
import formula.world
import formula.map

fun main() {

    //Show that the following sentences are consistent by identifying a world that satisfies each sentence:

    homework("2.1a", "(A =⇒ B) ∧ (A =⇒ ¬B)") { f ->
        f.world.map {
            if (f.evaluate(it)) println(it)
        }
    }
    homework("2.1b", "(A ∨ B) =⇒ (¬A ∧ ¬B)") { f ->
        f.world.iterator().forEach {
            if (f.evaluate(it)) println(it)
        }
    }
}

private fun homework(title: String, f: String, func: (Formula) -> Unit) {
    println("$title $f")
    func(Formula.parse(f))
    println()
}




