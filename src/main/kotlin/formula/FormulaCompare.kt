package formula

import org.jetbrains.kotlinx.dataframe.DataRow

fun Formula.equivalentTo(f2: Formula): Boolean = if (f2.vars.containsAll(vars)) implies(f2) && f2.implies(this) else false

fun Formula.Companion.exhaustive(f1: Formula, f2: Formula): Boolean {
    val world = World.generateDf((f1.vars + f2.vars).distinct().sortedBy { it.letter })
    val t1 = mutableListOf<DataRow<*>>()
    val t2 = mutableListOf<DataRow<*>>()
    world.formulaOnEach {
        if (f1.evaluate(it)) t1.add(it)
        if (f2.evaluate(it)) t2.add(it)
    }
    return t1.intersect(t2).isNotEmpty()
}

fun Formula.Companion.mutuallyExclusive(f1: Formula, f2: Formula): Boolean {
    val world = World.generateDf((f1.vars + f2.vars).distinct().sortedBy { it.letter })
    val t1 = mutableListOf<DataRow<*>>()
    val t2 = mutableListOf<DataRow<*>>()
    world.formulaOnEach {
        if (f1.evaluate(it)) t1.add(it)
        if (f2.evaluate(it)) t2.add(it)
    }
    return t1.intersect(t2).isEmpty()
}