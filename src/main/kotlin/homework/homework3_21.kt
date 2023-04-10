package homework

import org.jetbrains.kotlinx.dataframe.*
import org.jetbrains.kotlinx.dataframe.api.*

private fun main() {
    val world by column<String>()
    val K by column<String>()
    val Pr by column<Double>("Pr(.)")

    val df = dataFrameOf(world, K, Pr)
        .invoke(
            *listOf(
                listOf("w1", "david", 2.toDouble().div(3.toDouble())),
                listOf("w2", "dick", 1.toDouble().div(6.toDouble())),
                listOf("w3", "jane", 1.toDouble().div(6.toDouble())),
            ).flatten().toTypedArray()
        )
//        .addConditionalDistributionColumn(PrNotD, { filter { !D() } })
        .print()

    df.sumOf { Pr() }.print("Pr(.)")

}