package homework.bayesian_networks

import dataframe.Pr
import logic.printList


import bayesian.dsep
import bayesian.paths
import bayesian.valveState
import bayesian.valves

import org.jetbrains.kotlinx.dataframe.api.*
import printAsserting
import probability.chainRule
import probability.removeIndependencies
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

//New homework:
//● Chapter 4: 4.1, 4.2, 4.12, 4.23
//● Chapter 5: 5.1, 5.4, 5.6
class Chapter4_1 {
    val bn = Bn4_14_Exercise_1()

    @Test
    fun homework4_1a() {
        //List the Markovian assumptions asserted by the DAG.
        bn.Markov.joinToString("\n").apply {
            assertEquals(
                """
I(A, ∅, {B, E})
I(B, ∅, {A, C})
I(C, A, {B, D, E})
I(D, {A, B}, {C, E})
I(E, B, {A, C, D, F, G})
I(F, {C, D}, {A, B, E})
I(G, F, {A, B, C, D, E, H})
I(H, {E, F}, {A, B, C, D, G})
""".trim(), this
            )
        }
    }

    @Test
    fun homework4_1b() {
        //Express Pr(a, b, c, d, e, f, g, h) in terms of network parameters.
        bn.parameterStatement.chainRule().map { it.removeIndependencies(bn.Markov) }.apply {
            assertEquals("Pr(H|F,E), Pr(G|F), Pr(F|D,C), Pr(E|B), Pr(D|A,B), Pr(C|A), Pr(A), Pr(B)", joinToString(", "))
        }

    }

    @Test
    fun homework4_1c() {
        //Compute Pr(A = 0, B = 0). Justify your answers.
        bn.apply {
            val cpA = cpts["Pr(A)"]!!.filter { colA() == 0 }.sumOf { Pr() }.printAsserting(0.8)
            val cpB = cpts["Pr(B)"]!!.filter { colB() == 0 }.sumOf { Pr() }.printAsserting(0.3)
            val cpEB = cpts["Pr(E|B)"]!!.filter { colB() == 0 }.sumOf { Pr() }.printAsserting(1.0)
            val cpABD = cpts["Pr(D|A,B)"]!!.filter { colA() == 0 && colB() == 0 }.sumOf { Pr() }.printAsserting(1.0)
            (cpA * cpB * cpEB * cpABD).printAsserting(0.24, "Pr(A = 0, B = 0)")

            parameterStatement.chainRule().map { it.removeIndependencies(bn.Markov) }.printList()

            //Compute Pr(E = 1 | A = 1). Justify your answers.
            chainedIndependantParameterStatements.printList()

            dsep(E, A).printAsserting(true)

//                .map { it.independence(markov) }.printList().also { println("size ${it.size}")}
//                .joinToString("").printAsserting("Pr(E)", "E and A are independent, the answer is 1")
////
            cpts["Pr(E|B)"]!!.filter { colE() == 1 }.sumOf { Pr() }.printAsserting(1.0)
        }
    }

    @Test
    fun homework4_1d_1() {
        //True or false? Why?
        bn.apply {
            Markov.printList()
            assertTrue(dsep(listOf(A), listOf(B, H), listOf(E)), "dsep(A, BH, E) ANSWER TRUE")
            paths(A, E).onEach {
                println("path $it")
                valves(it).onEach {
                    println("  $it ${valveState(it, listOf(B, H))}")
                }
            }
        }
    }

    @Test
    fun homework4_1d_2() {
        //True or false? Why?
        bn.apply {
            Markov.printList()
            assertFalse(dsep(listOf(G), listOf(D), listOf(E)), "dsep(G, D, E) ANSWER FALSE")
            paths(A, E).onEach {
                println("path $it")
                valves(it).onEach {
                    println("  $it ${valveState(it, listOf(B, H))}")
                }
            }
        }
    }

    @Test
    fun homework4_1d_3() {
        //True or false? Why?
        bn.apply {
            Markov.printList()
            assertFalse(dsep(listOf(A, B), listOf(F), listOf(G, H)), "dsep(AB, F, GH) ANSWER FALSE")
            paths(A, E).onEach {
                println("path $it")
                valves(it).onEach {
                    println("  $it ${valveState(it, listOf(B, H))}")
                }
            }
        }
    }
}
