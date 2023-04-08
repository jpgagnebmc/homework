package homework

import formula.Formula
import formula.onEach
import formula.valid
import formula.world


fun main() {

    // Which of the following sentences are valid? If a sentence is not valid, identify a world that
    // does not satisfy the sentence.

    homework("2.2a", "(A ∧ (A =⇒ B)) =⇒ B") { f ->
        println("valid ${f.valid}")
    }
    homework("2.2b", "(A ∧ B) ∨ (A ∧ ¬B)") { f ->
        println("valid ${f.valid}")
        f.world.onEach {
            if (!f.evaluate(it)) println("invalid $it")
        }
    }
    homework("2.2c", "(A =⇒ B) =⇒ (¬B =⇒ ¬A)") { f ->
        println("valid ${f.valid}")
    }

}

private fun homework(title: String, f: String, func: (Formula) -> Unit) {
    println("$title $f")
    func(Formula.parse(f))
    println()
}




