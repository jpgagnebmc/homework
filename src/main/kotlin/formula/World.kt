package formula

import homework.pow
import org.jetbrains.kotlinx.dataframe.AnyFrame
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.DataRow
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import kotlin.math.max
import kotlin.math.pow

data class World(val vars: List<Formula.Var>, val df: DataFrame<*>) {
    companion object {
        fun generateDf(vars: List<Formula.Var>): AnyFrame {
            val varCount = vars.size
            val rowCount = max(2, varCount.pow(2))
            val rows = mutableListOf<List<Boolean>>()
            for (i in 0 until rowCount) {
                val row = mutableListOf<Boolean>()
                for (j in varCount - 1 downTo 0) {
                    row += (i / 2.0.pow(j.toDouble()).toInt() % 2) == 1
                }
                rows.add(0, row)
            }
            return dataFrameOf(*vars.map { it.letter }.toTypedArray()).invoke(*rows.flatten().toTypedArray())
        }
    }
}

val Formula.world: AnyFrame
    get() = World.generateDf(vars)

fun <T> DataFrame<T>.formulaOnEach(func: (DataRow<T>) -> Unit) {
    iterator().forEach {
        func(it)
    }
}

fun <T, R> DataFrame<T>.formulaMap(func: (DataRow<T>) -> R): List<R> = mutableListOf<R>().apply {
    this@formulaMap.iterator().forEach {
        add(func(it))
    }
}.toList()

fun <T> DataFrame<T>.formulaFilter(func: (DataRow<T>) -> Boolean): List<DataRow<T>> = mutableListOf<DataRow<T>>().apply {
    this@formulaFilter.iterator().forEach {
        if (func(it)) add(it)
    }
}.toList()