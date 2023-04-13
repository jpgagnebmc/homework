package bayesian


import logic.printList
import homework.bayesian_networks.Bn4_1_Alarm
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestValves {


    @Test
    fun valveTypes() {
        val A = Node("A")
        val B = Node("B")
        val C = Node("C")
        assertTrue(Valve.from(Rel(A, B), Rel(B, C)) is Valve.Sequential)
        assertTrue(Valve.from(Rel(B, A), Rel(B, C)) is Valve.Divergent)
        assertTrue(Valve.from(Rel(A, B), Rel(C, B)) is Valve.Convergent)
    }

    @Test
    fun testPage53() {
        //For example, the sequential valve E→A→C in Figure 4.8 is closed iff we know the value
        //of variable A, otherwise an earthquake E may change our belief in getting a call C.


        Bn4_1_Alarm().apply {
            Markov.printList()
            Valve.from(Rel(E, A), Rel(A, C)).apply {
                assertEquals(W, A)
                assertTrue { this is Valve.Sequential }
                assertTrue { valveState(this, listOf(A)) is Valve.State.Closed }
                assertTrue { valveState(this, listOf(B)) is Valve.State.Opened }
            }
            //A divergent valve (←W→) is closed iff variable W appears in Z.
            //For example, the divergent valve R←E→A in Figure 4.8 is closed iff we know the value
            //of variable E, otherwise a radio report on an earthquake may change our belief in the
            //alarm triggering.
            Valve.from(Rel(E, R), Rel(E, A)).apply {
                assertEquals(W, E)
                assertTrue { this is Valve.Divergent }
                assertTrue { valveState(this, listOf(E)) is Valve.State.Closed }
                assertTrue { valveState(this, listOf(B)) is Valve.State.Opened }
            }
            //A convergent valve (→W←) is closed iff neither variable W nor any of its descendants appears in Z.
            //For example, the convergent valve E→A←B in Figure 4.8 is closed iff neither the value
            //of variable A nor the value of C are known, otherwise, a burglary may change our belief in an earthquake.
            Valve.from(Rel(E, A), Rel(B, A)).apply {
                assertEquals(W, A)
                assertTrue { this is Valve.Convergent }
//                assertTrue { valveState(this, listOf(C)) is Valve.State.Closed }
//                assertTrue { valveState(this, listOf(R)) is Valve.State.Closed }
//                assertTrue { valveState(this, listOf(A)) is Valve.State.Opened }
//                assertTrue { valveState(this, listOf()) is Valve.State.Closed }
            }


            assertTrue { valveState(Valve.from(Rel(E, R), Rel(E, A)), listOf(E, C)) is Valve.State.Closed } //R←E→A
            assertTrue { valveState(Valve.from(Rel(E, A), Rel(B, A)), listOf(E, C)) is Valve.State.Opened } //E→A←B


        }
    }
}

