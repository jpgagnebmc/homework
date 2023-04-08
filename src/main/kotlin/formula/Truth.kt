package formula

import org.jetbrains.kotlinx.dataframe.AnyFrame
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf

val Formula.truth: AnyFrame
    get() {
        val sortedSubformulas = subformulas.sortedBy { it.source.length }
        val rows = mutableListOf<List<Boolean>>()
        world.apply {
            iterator().forEach { row ->
                rows.add(sortedSubformulas.map { it.evaluate(row) })
            }
        }
        return dataFrameOf(*sortedSubformulas.map { it.source }.toTypedArray()).invoke(*rows.flatten().toTypedArray())
    }