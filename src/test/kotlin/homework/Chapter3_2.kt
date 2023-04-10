package homework

import org.jetbrains.kotlinx.dataframe.DataColumn
import org.jetbrains.kotlinx.dataframe.api.column
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.dataframe.api.filter
import org.jetbrains.kotlinx.dataframe.api.sumOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class Chapter3_2 {
    private val A by column<Boolean>()
    private val B by column<Boolean>()
    private val C by column<Boolean>()
    private val Pr by column<Double>("Pr(.)")
    private val PrAorB by column<Double>("Pr(.|A v B)")
    private val PrB by column<Double>("Pr(.|B)")
    private val PrC by column<Double>("Pr(.|C)")
    val data = raw.lines().chunked(8)
    val df = dataFrameOf("world", A.name(), B.name(), C.name(), Pr.name())
        .invoke(
            DataColumn.create<String>("world", data[0]),
            DataColumn.create<Boolean>(A.name(), data[1].map { it.toBooleanStrict() }),
            DataColumn.create<Boolean>(B.name(), data[2].map { it.toBooleanStrict() }),
            DataColumn.create<Boolean>(C.name(), data[3].map { it.toBooleanStrict() }),
            DataColumn.create<Double>(Pr.name(), data[4].map { it.toDouble() }),
        ).addConditionalDistributionColumn(PrAorB, { filter { A() || B() } })

    @Test
    fun homework3_2() {
        //What is Pr(A = true ∨ B = true)?
        assertEquals(0.625, df.filter { A() || B() }.sumOf { Pr() }, "Pr(A = true ∨ B = true)")

        df.print()

        assertEquals(0.8, df.filter { A() }.sumOf { PrAorB() }, "Pr(A = true|A = true ∨ B = true)")
        assertEquals(0.4, df.filter { B() }.sumOf { PrAorB() }, "Pr(B = true|A = true ∨ B = true)")
        assertEquals(0.52, df.filter { C() }.sumOf { PrAorB() }, "Pr(C = true|A = true ∨ B = true)")

        assertNotEquals(df.filter { B() }.sumOf { PrAorB() }, df.filter { C() }.sumOf { PrAorB() }, "A and C are independent given A = true ∨ B = true")
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