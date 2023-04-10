package formula

import homework.pow
import org.jetbrains.kotlinx.dataframe.AnyFrame
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import kotlin.math.max
import kotlin.math.pow

data class World(val vars: List<Formula.Var>, val df: DataFrame<*>) {
    companion object {
        fun generateDf(vars: List<Formula.Var>): AnyFrame {
            val distinctVars = vars.distinct().sortedBy { it.letter }
            val varCount = distinctVars.size
            val rowCount = max(2, varCount.pow(2))
            val rows = mutableListOf<List<Boolean>>()
            for (i in 0 until rowCount) {
                val row = mutableListOf<Boolean>()
                for (j in varCount - 1 downTo 0) {
                    row += (i / 2.0.pow(j.toDouble()).toInt() % 2) == 1
                }
                rows.add(0, row)
            }
            return dataFrameOf("world", *distinctVars.map { it.letter }.toTypedArray())
                .invoke(*rows.mapIndexed { x, chunk -> mutableListOf("w${x + 1}") + chunk.toTypedArray() }.flatten().toTypedArray())
        }
    }
}
val Formula.world: AnyFrame
    get() = World.generateDf(vars)

