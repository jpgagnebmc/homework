package homework.bayesian_networks

import bayesian.dsep
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Homework4_2 {
    @Test
    fun homework4_2() {
        //Consider the DAG G in Figure 4.15. Determine if any of dsepG (Ai , ∅, Bi ), dsepG (Ai , ∅, Ci ),
        //or dsepG (Bi , ∅, Ci ) hold for i = 1, 2, 3.

        Bn4_15_DAG().apply {

            assertTrue(dsep(A1, B1))
            assertFalse(dsep(A2, B2))
            assertFalse(dsep(A3, B3))

            assertTrue(dsep(A1, C1))
            assertTrue(dsep(A2, C2))
            assertFalse(dsep(A3, C3))

            assertTrue(dsep(B1, C1))
            assertFalse(dsep(B2, C2))
            assertFalse(dsep(B3, C3))
        }
    }
}