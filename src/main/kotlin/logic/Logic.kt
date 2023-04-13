package logic

import org.jetbrains.kotlinx.dataframe.DataRow
import org.jetbrains.kotlinx.dataframe.api.*

fun Formula(stmt: String): Logic = Logic.parse(stmt)

sealed interface Logic {
    val source: String
    val vars: List<Var>
    val subformulas: List<Subformula>
    fun evaluate(row: DataRow<Any?>): Boolean
    override fun hashCode(): Int

    companion object {
        fun parse(f1: String): Logic = LogicParser(f1).model()
    }

    data class And(val f1: Logic, val f2: Logic) : Logic, Subformula {
        override val source: String
            get() = "${f1.source} ∧ ${f2.source}"
        override val vars: List<Var>
            get() = (f1.vars + f2.vars).distinct()
        override val subformulas: List<Subformula>
            get() = (f1.subformulas + f2.subformulas + this).distinct()


        override fun evaluate(row: DataRow<Any?>): Boolean = f1.evaluate(row) && f2.evaluate(row)
    }

    data class Or(val f1: Logic, val f2: Logic) : Logic, Subformula {
        override val source: String
            get() = "${f1.source} ∨ ${f2.source}"
        override val vars: List<Var>
            get() = (f1.vars + f2.vars).distinct()
        override val subformulas: List<Subformula>
            get() = (f1.subformulas + f2.subformulas + this).distinct()

        override fun evaluate(row: DataRow<Any?>): Boolean = f1.evaluate(row) || f2.evaluate(row)
    }

    data class Var(val letter: String) : Logic, Subformula {
        override val source: String
            get() = letter
        override val vars: List<Var>
            get() = listOf(this)
        override val subformulas: List<Subformula>
            get() = listOf(this)

        override fun evaluate(row: DataRow<Any?>): Boolean = row[letter].toString().toBooleanStrict()
    }

    data class Group(val f: Logic) : Logic {
        override val source: String
            get() = "(${f.source})"
        override val vars: List<Var>
            get() = f.vars.distinct()
        override val subformulas: List<Subformula>
            get() = f.subformulas

        override fun evaluate(row: DataRow<Any?>): Boolean = f.evaluate(row)
    }

    data class Not(val f: Logic) : Logic, Subformula {
        override val source: String
            get() = "!${f.source}"
        override val vars: List<Var>
            get() = f.vars.distinct()
        override val subformulas: List<Subformula>
            get() = listOf(this)

        override fun evaluate(row: DataRow<Any?>): Boolean = !f.evaluate(row)
    }

    data class Implies(val f1: Logic, val f2: Logic) : Logic, Subformula {
        override val source: String
            get() = "${f1.source} -> ${f2.source}"
        override val vars: List<Var>
            get() = (f1.vars + f2.vars).distinct()
        override val subformulas: List<Subformula>
            get() = (f1.subformulas + f2.subformulas + this).distinct()

        override fun evaluate(row: DataRow<Any?>): Boolean {
            val b1 = f1.evaluate(row)
            return when {
                !f2.evaluate(row) && b1 -> false
                else -> true
            }
        }
    }
}

sealed interface Subformula : Logic {

}

fun Logic.implies(f2: Logic): Boolean {
    return World.generateDf(vars + f2.vars).map {
        Pair(evaluate(it), f2.evaluate(it))
    }.all { it.first == it.second }
}