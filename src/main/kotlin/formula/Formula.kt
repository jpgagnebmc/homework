package formula

import org.jetbrains.kotlinx.dataframe.DataRow
import org.jetbrains.kotlinx.dataframe.api.*

fun Formula(stmt: String): Formula = Formula.parse(stmt)

sealed interface Formula {
    val source: String
    val vars: List<Var>
    val subformulas: List<Subformula>
    fun evaluate(row: DataRow<Any?>): Boolean
    override fun hashCode(): Int

    companion object {
        fun parse(f1: String): Formula = FormulaParser(f1).model()
    }

    data class And(val f1: Formula, val f2: Formula) : Formula, Subformula {
        override val source: String
            get() = "${f1.source} ∧ ${f2.source}"
        override val vars: List<Var>
            get() = (f1.vars + f2.vars).distinct()
        override val subformulas: List<Subformula>
            get() = (f1.subformulas + f2.subformulas + this).distinct()


        override fun evaluate(row: DataRow<Any?>): Boolean = f1.evaluate(row) && f2.evaluate(row)
    }

    data class Or(val f1: Formula, val f2: Formula) : Formula, Subformula {
        override val source: String
            get() = "${f1.source} ∨ ${f2.source}"
        override val vars: List<Var>
            get() = (f1.vars + f2.vars).distinct()
        override val subformulas: List<Subformula>
            get() = (f1.subformulas + f2.subformulas + this).distinct()

        override fun evaluate(row: DataRow<Any?>): Boolean = f1.evaluate(row) || f2.evaluate(row)
    }

    data class Var(val letter: String) : Formula, Subformula {
        override val source: String
            get() = letter
        override val vars: List<Var>
            get() = listOf(this)
        override val subformulas: List<Subformula>
            get() = listOf(this)

        override fun evaluate(row: DataRow<Any?>): Boolean = row[letter].toString().toBooleanStrict()
    }

    data class Group(val f: Formula) : Formula {
        override val source: String
            get() = "(${f.source})"
        override val vars: List<Var>
            get() = f.vars.distinct()
        override val subformulas: List<Subformula>
            get() = f.subformulas

        override fun evaluate(row: DataRow<Any?>): Boolean = f.evaluate(row)
    }

    data class Not(val f: Formula) : Formula, Subformula {
        override val source: String
            get() = "!${f.source}"
        override val vars: List<Var>
            get() = f.vars.distinct()
        override val subformulas: List<Subformula>
            get() = listOf(this)

        override fun evaluate(row: DataRow<Any?>): Boolean = !f.evaluate(row)
    }

    data class Implies(val f1: Formula, val f2: Formula) : Formula, Subformula {
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

sealed interface Subformula : Formula {

}

fun Formula.implies(f2: Formula): Boolean {
    return World.generateDf(vars + f2.vars).map {
        Pair(evaluate(it), f2.evaluate(it))
    }.all { it.first == it.second }
}