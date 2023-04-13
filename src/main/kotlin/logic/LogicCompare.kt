package logic

import org.jetbrains.kotlinx.dataframe.DataRow
import org.jetbrains.kotlinx.dataframe.api.*

fun Logic.equivalentTo(f2: Logic): Boolean = implies(f2) && f2.implies(this)


fun Logic.Companion.exhaustiveIntersection(f1: Logic, f2: Logic): Set<DataRow<*>> {
    val world = World.generateDf(f1.vars + f2.vars)
    val t1 = mutableListOf<DataRow<*>>()
    val t2 = mutableListOf<DataRow<*>>()
    world.forEach {
        if (f1.evaluate(it)) t1.add(it)
        if (f2.evaluate(it)) t2.add(it)
    }
    return t1.intersect(t2)
}

fun Logic.Companion.exhaustive(f1: Logic, f2: Logic): Boolean {
    val world = World.generateDf(f1.vars + f2.vars)
    return world.filter { f1.evaluate(it) || f2.evaluate(it) }.rowsCount() == world.rowsCount()
}

fun Logic.Companion.mutuallyExclusive(f1: Logic, f2: Logic): Boolean {
    val world = World.generateDf(f1.vars + f2.vars).print("combined\n")
    val t1 = mutableListOf<DataRow<*>>()
    val t2 = mutableListOf<DataRow<*>>()
    world.forEach {
        if (f1.evaluate(it)) t1.add(it)
        if (f2.evaluate(it)) t2.add(it)
    }
    return t1.intersect(t2).print("mutuallyExclusive intersect").isEmpty()
}