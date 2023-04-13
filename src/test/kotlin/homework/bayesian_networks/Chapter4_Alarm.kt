package homework.bayesian_networks

import logic.print
import logic.printList
import probability.chainRule
import probability.removeIndependencies
import kotlin.test.Test
import kotlin.test.assertEquals

class TestBn4_1_Alarm {
    @Test
    fun parents() {
        Bn4_1_Alarm().apply { assertEquals(listOf(E, B), Parents(A)) }
    }

    @Test
    fun descendants() {
        Bn4_1_Alarm().apply { assertEquals(listOf(A, C), Descendants(B)) }
    }

    @Test
    fun nonDescendants() {
        Bn4_1_Alarm().apply { assertEquals(listOf(E, R), Non_Descendants(B)) }
    }

    @Test
    fun markov() {
        assertEquals(
            """
I(E, ∅, B)
I(B, ∅, {E, R})
I(R, E, {A, B, C})
I(A, {B, E}, R)
I(C, A, {B, E, R})    
""".trim(), Bn4_1_Alarm().run { Markov.joinToString("\n") }
        )
    }

    @Test
    fun statements() {
        Bn4_1_Alarm().apply {

            parameterStatement.chainRule().printList()
                .map { it.removeIndependencies(Markov) }.printList()
                .joinToString("").print()


//            Probability(listOf(C, A, R, B, E)).chainRule().printList()
//                .map { it.independence(markov) }.printList()
//                .joinToString("").print()

            //Pr(r|c,a,e,b),Pr(c|a,e,b),Pr(a|e,b),Pr(e|b),Pr(b)

            //Pr(r|c, a, e, b) = Pr(r|e)
            //Pr(c|a, e, b) = Pr(c|a)
            //Pr(e|b) = Pr(e).


//            assertEquals("Pr(E,B,R,A,C)", parameterStatement)
            Markov.printList()
//            parameterGivenStatements.printList()
//            parameterStatement.print()
//            parameterStatements.print()
//            parameterGivenStatements.print()
//            markov.printList()
        }
    }


}