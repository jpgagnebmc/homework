package homework.probability_calculus

import dataframe.Pr
import dataframe.World
import dataframe.addConditionalDistributionColumn
import logic.print
import org.jetbrains.kotlinx.dataframe.DataColumn
import org.jetbrains.kotlinx.dataframe.api.column
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.dataframe.api.filter
import org.jetbrains.kotlinx.dataframe.api.sumOf
import kotlin.test.Test

class Chapter3_6 {
    @Test
    fun homework3_6() {
        val Older by column<String>()
        val Younger by column<String>()

        val PrYoungerBoy by column<Double>("Pr(Younger = boy)")
        val PrNotGirlGirl by column<Double>("Pr(!(Older = girl ∧ Younger = girl))")

        val df = dataFrameOf(World, Older, Younger, Pr)
            .invoke(
                DataColumn.create<String>(World.name(), listOf("w0", "w1", "w2", "w3")),
                DataColumn.create<String>(Older.name(), listOf("girl", "boy", "boy", "girl")),
                DataColumn.create<String>(Younger.name(), listOf("girl", "boy", "girl", "boy")),
                DataColumn.create<Double>(Pr.name(), listOf<Double>(0.25, 0.25, 0.25, 0.25)),
            ).addConditionalDistributionColumn(PrYoungerBoy, { filter { Younger() == "boy" } })
            .addConditionalDistributionColumn(PrNotGirlGirl, { filter { !(Younger() == "girl" && Older() == "girl") } })
            .print()

        println("3.6. Consider a family with two children, ages four and nine:")
        df.filter { Older() == "boy" }.sumOf { Pr() }.print("  (a) What is the probability that the older child is a boy?")
        df.filter { Older() == "boy" }.sumOf { PrYoungerBoy() }.print("  (b) What is the probability that the older child is a boy given that the younger child is a boy?")
        df.filter { Older() == "boy" }.sumOf { PrNotGirlGirl() }.print("  (c) What is the probability that the older child is a boy given that at least one of the children is a boy?")
        df.filter { Older() == "boy" && Younger() == "boy" }.sumOf { PrNotGirlGirl() }.print("  (d) What is the probability that both children are boys given that at least one of them is a boy?")
    }

}