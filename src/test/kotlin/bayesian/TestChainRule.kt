package bayesian

import dataframe.Pr
import homework.bayesian_networks.Bn4_4_Sprinkler
import logic.print
import org.jetbrains.kotlinx.dataframe.api.filter
import org.jetbrains.kotlinx.dataframe.api.sumOf
import printAsserting
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Figure4_4 {
    val bn = Bn4_4_Sprinkler()


    @Test
    fun parameterizing() {
        assertEquals("Pr(E|C), Pr(D|C,B), Pr(C|A), Pr(B|A), Pr(A)", bn.chainedIndependantParameterStatements.joinToString(", "))
        bn.cpts.values.onEach {
            it.sumOf { Pr() }.print()
            it.print()
        }
    }

    @Test
    fun chainRule1() {
        //Pr(a, b, c̄, d, ē) = θa θb|a θc̄|a θd|b,c̄ θē|c̄
        bn.apply {
            val cpA = cpts["Pr(A)"]!!.filter { colA() }.sumOf { Pr() }.printAsserting(0.6)
            val cpB = cpts["Pr(B|A)"]!!.filter { colA() && colB() }.sumOf { Pr() }.printAsserting(0.2)
            val cpC = cpts["Pr(C|A)"]!!.filter { colA() && !colC() }.sumOf { Pr() }.printAsserting(0.2)
            val cpD = cpts["Pr(D|B,C)"]!!.filter { colD() && colB() && !colC() }.sumOf { Pr() }.printAsserting(0.9)
            val cpE = cpts["Pr(E|C)"]!!.filter { !colC() && !colE() }.sumOf { Pr() }.printAsserting(1.0)

            (cpA * cpB * cpC * cpD * cpE).printAsserting(0.0216)
        }
    }

    @Test
    fun chainRule2() {
        //Pr(a, b, c̄, d, ē) = θa θb|a θc̄|a θd|b,c̄ θē|c̄
        bn.apply {
            val cpA = cpts["Pr(A)"]!!.filter { !colA() }.sumOf { Pr() }.printAsserting(0.4)
            val cpB = cpts["Pr(B|A)"]!!.filter { !colA() && !colB() }.sumOf { Pr() }.printAsserting(0.25)
            val cpC = cpts["Pr(C|A)"]!!.filter { !colA() && !colC() }.sumOf { Pr() }.printAsserting(0.9)
            val cpD = cpts["Pr(D|B,C)"]!!.filter { !colD() && !colB() && !colC() }.sumOf { Pr() }.printAsserting(1.0)
            val cpE = cpts["Pr(E|C)"]!!.filter { !colC() && !colE() }.sumOf { Pr() }.printAsserting(1.0)
            (cpA * cpB * cpC * cpD * cpE).printAsserting(0.09000000000000001)
        }
    }

    @Test
    fun independance4_4() {
        bn.apply {
            assertTrue(dsep(listOf(D), listOf(A, C), listOf(E)))
        }
    }
}