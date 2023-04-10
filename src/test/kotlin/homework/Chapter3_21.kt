package homework

import dataframe.Pr
import dataframe.World
import formula.print
import org.jetbrains.kotlinx.dataframe.api.column
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.dataframe.api.filter
import org.jetbrains.kotlinx.dataframe.api.sumOf
import kotlin.test.Test
import kotlin.test.assertEquals

class Chapter3_21 {
    //Consider Section 3.6.3 and the investigator Rich with his state of belief regarding murder suspects:
    //Suppose now that Rich receives some new evidence that triples his odds of the killer being
    //male. What is the new belief of Rich that David is the killer? What would this belief be if after
    //accommodating the evidence, Rich’s belief in the killer being male is 93.75%?
    //   World Killer    Male    Pr(.)
    // 0    w1  david 0.46875 0.666667
    // 1    w2   dick 0.46875 0.166667
    // 2    w3   jane 0.06250 0.166667
    val Killer by column<String>()
    val Male by column<String>()
    val df = dataFrameOf(World, Killer, Male, Pr)
        .invoke(
            *listOf(
                listOf("w1", "david", 0.9375 / 2, 2.toDouble().div(3.toDouble())),
                listOf("w2", "dick", 0.9375 / 2, 1.toDouble().div(6.toDouble())),
                listOf("w3", "jane", 1 - 0.9375, 1.toDouble().div(6.toDouble())),
            ).flatten().toTypedArray()
        ).print()

    @Test
    fun homework3_21() {
        val PrKillerDavid = df.filter { Killer() == "david" }.sumOf { Pr() }.also { assertEquals(0.6666666666666666, it) }.print("PrKillerDavid")
        val PrKillerNotDavid = df.filter { Killer() != "david" }.sumOf { Pr() }.also { assertEquals(0.3333333333333333, it) }.print("PrKillerNotDavid")
        val maleCount = 2.0
        val oddsIncreaseForMale = 3.0
        val davidOddsIncrease = (oddsIncreaseForMale / maleCount).print("IncreasedOdds")

        ((davidOddsIncrease * PrKillerDavid) / ((davidOddsIncrease * PrKillerDavid) + (PrKillerNotDavid)))
            .also { assertEquals(0.75, it) }
            .print("probability that David is the killer")
    }

}
