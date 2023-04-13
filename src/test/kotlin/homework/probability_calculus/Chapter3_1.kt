package homework.probability_calculus

import dataframe.A
import dataframe.B
import dataframe.C
import dataframe.Pr
import dataframe.PrC
import dataframe.World
import dataframe.addConditionalDistributionColumn
import logic.print
import org.jetbrains.kotlinx.dataframe.DataColumn
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.dataframe.api.filter
import org.jetbrains.kotlinx.dataframe.api.sumOf
import kotlin.test.Test
import kotlin.test.assertEquals

class Chapter3_1 {
    val data = raw.lines().chunked(8)
    val df = dataFrameOf(World.name(), A.name(), B.name(), C.name(), Pr.name())
        .invoke(
            DataColumn.create<String>(World.name(), data[0]),
            DataColumn.create<Boolean>(A.name(), data[1].map { it.toBooleanStrict() }),
            DataColumn.create<Boolean>(B.name(), data[2].map { it.toBooleanStrict() }),
            DataColumn.create<Boolean>(C.name(), data[3].map { it.toBooleanStrict() }),
            DataColumn.create<Double>(Pr.name(), data[4].map { it.toDouble() }),
        ).addConditionalDistributionColumn(PrC, { filter { C() } })

    @Test
    fun homework3_1() {
        //What is Pr(A = true)? Pr(B = true)? Pr(C = true)?
        assertEquals(0.5, df.filter { A() }.sumOf { Pr() }, "Pr(A = true)")
        assertEquals(0.25, df.filter { B() }.sumOf { Pr() }, "Pr(B = true)")
        assertEquals(0.4, df.filter { C() }.sumOf { Pr() }, "Pr(C = true)")

        //Update the distribution by conditioning on the event C = true, that is, construct the conditional distribution Pr(.|C = true).
        df.print()

        //What is Pr(A = true|C = true)? Pr(B = true|C = true)

        assertEquals(0.75, df.filter { A() }.sumOf { PrC() })
        assertEquals(0.24999999999999997, df.filter { B() }.sumOf { PrC() })
        assertEquals(1.0, df.filter { C() }.sumOf { PrC() })

        //Is the event A = true independent of the event C = true
        // Pr(A) 0.5
        // Pr(A|C) 0.75
        // A and C are independent

        //Is the event B = true independent of the event C = true
        // Pr(B) 0.25
        // Pr(B|C) 0.24999999999999997
        // A and B are NOT independent
    }

    val raw: String
        get() = """
ω1
ω2
ω3
ω4
ω5
ω6
ω7
ω8
true
true
true
true
false
false
false
false
true
true
false
false
true
true
false
false
true
false
true
false
true
false
true
false
.075
.050
.225
.150
.025
.100
.075
.300    
""".trimIndent()
}