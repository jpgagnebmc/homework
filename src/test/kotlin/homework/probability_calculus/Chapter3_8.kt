package homework.probability_calculus

import dataframe.D
import dataframe.Pr
import dataframe.PrD
import dataframe.PrNotD
import dataframe.T
import dataframe.World
import dataframe.addConditionalDistributionColumn
import logic.print
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.dataframe.api.filter
import org.jetbrains.kotlinx.dataframe.api.sumOf
import kotlin.math.pow
import kotlin.test.Test
import kotlin.test.assertEquals

class Chapter3_8 {
    //        Suppose that we have a patient who was just tested for a particular disease and the test
    //        came out positive. We know that one in every thousand people has this disease. We also
    //        know that the test is not reliable: it has a false positive rate of 2% and a false negative rate
    //        of 5%. We have seen previously that the probability of having the disease is ≈ 4.5% given a
    //        positive test result. Suppose that the test is repeated n times and all tests come out positive.
    //        What is the smallest n for which the belief in the disease is greater than 95%, assuming the
    //        errors of various tests are independent? Justify your answer.
    @Test
    fun homework3_8() {

        val prior = 0.001
        val falsePositive = 0.02
        val falseNegative = 0.05

        val df = dataFrameOf(World, D, T, Pr)
            .invoke(
                *listOf(
                    listOf("w1", true, true, (1 - falseNegative) * prior),
                    listOf("w2", true, false, falseNegative * prior),
                    listOf("w3", false, true, falsePositive * (1 - prior)),
                    listOf("w4", false, false, (1 - falsePositive) * (1 - prior)),
                ).flatten().toTypedArray()
            ).addConditionalDistributionColumn(PrD, { filter { D() } })
            .addConditionalDistributionColumn(PrNotD, { filter { !D() } })
            .print()

        //Pr(T) = Pr(T|D)Pr(D) + Pr(T|¬D)Pr(¬D)
        assertEquals(0.001, prior)
        val conditional = df.filter { T() }.sumOf { PrD() }.also { assertEquals(0.95, it, "Pr(T|D)") }
        val prTNotD = df.filter { T() }.sumOf { PrNotD() }.also { assertEquals(0.02, it, "Pr(T|!D)") }

        //P(++) = P(+│D)²P(D)+P(+│¬D)²P(¬D)

        assertEquals(0.04538939321548017, bayes(conditional, prior, falsePositive, 1).print())
        assertEquals(0.7295906612395361, bayes(conditional, prior, falsePositive, 2).print())
        assertEquals(1.097800124109193, bayes(conditional, prior, falsePositive, 3).print())
    }

    private fun bayes(conditional: Double, prior: Double, prTFalsePositive: Double, i: Int): Double {
        val marginal = (conditional.pow(i) * prior) + prTFalsePositive.pow(i) * (1 - prior)
        return (conditional * prior / marginal)
    }

    fun Double.format(scale: Int) = "%.${scale}f".format(this)
}