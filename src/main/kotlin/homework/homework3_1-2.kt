package homework

import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.*

/*
● Chapter 2: 2.1, 2.3, 2.7
● Chapter 3: 3.2, 3.6, 3.8, 3.18, 3.21, 3.27
 */
private val A by column<Boolean>()
private val B by column<Boolean>()
private val C by column<Boolean>()
private val Pr by column<Double>("Pr(.)")
private val PrA by column<Double>("Pr(.|A)")
private val PrB by column<Double>("Pr(.|B)")
private val PrC by column<Double>("Pr(.|C)")
public fun main() {
    val data = raw.lines().chunked(8)
    val jd = dataFrameOf("world", "A", "B", "C", "Pr(.)")
        .invoke(
            DataColumn.create<String>("world", data[0]),
            DataColumn.create<Boolean>("A", data[1].map { it.toBooleanStrict() }),
            DataColumn.create<Boolean>("B", data[2].map { it.toBooleanStrict() }),
            DataColumn.create<Boolean>("C", data[3].map { it.toBooleanStrict() }),
            DataColumn.create<Double>("Pr(.)", data[4].map { it.toDouble() }),
        )
    //   world     A     B     C    Pr
    // 0    ω1  true  true  true 0.075
    // 1    ω2  true  true false 0.050
    // 2    ω3  true false  true 0.225
    // 3    ω4  true false false 0.150
    // 4    ω5 false  true  true 0.025
    // 5    ω6 false  true false 0.100
    // 6    ω7 false false  true 0.075
    // 7    ω8 false false false 0.300

    println("3.1a What is Pr(A = true)? Pr(B = true)? Pr(C = true)?")
    jd.filter { A() }.sumOf { Pr() }.print("  prA")
    jd.filter { B() }.sumOf { Pr() }.print("  prB")
    jd.filter { C() }.sumOf { Pr() }.print("  prC")

    println("\n3.1b Update the distribution by conditioning on the event C = true, that is, construct the conditional distribution Pr(.|C = true).")
    jd.addConditionalDistributionColumn(PrC, { filter { C() } }).print("\n")

    println("3.1c What is Pr(A = true|C = true)? Pr(B = true|C = true)?")
    jd.addConditionalDistributionColumn(PrC, { filter { C() } })
        .filter { A() }.sumOf { PrC() }.print("  Pr(A = true|C = true)")
    jd.addConditionalDistributionColumn(PrC, { filter { C() } })
        .filter { B() }.sumOf { PrC() }.print("  Pr(B = true|C = true)")

    println("\n3.1d Is the event A = true independent of the event C = true   ANSWER NO")
    jd.filter { A() }.sumOf { Pr() }.print("  Pr(A = true)")
    jd.addConditionalDistributionColumn(PrC, { filter { C() } }).filter { A() }.sumOf { Pr() }.print("  Pr(A|C)")

    println("\n3.1d Is B = true independent of C = true   ANSWER NO")

    jd.filter { B() }.sumOf { Pr() }.print("  Pr(B = true)")
    jd.addConditionalDistributionColumn(PrC, { filter { C() } }).filter { B() }.sumOf { Pr() }.print("  Pr(A|B)")

    println("\n3.2a What is Pr(A = true ∨ B = true)?")
    jd.filter { A() || B() }.sumOf { Pr() }.print("  Pr(A v B)")

    println("\n3.2b Update the distribution by conditioning on the event A = true ∨ B = true, that is, construct the conditional distribution Pr(.|A = true ∨ B = true).")
    val PrCorB by column<Double>("Pr(.|A v B)")
    jd.addConditionalDistributionColumn(PrCorB, { filter { C() || B() } }).print()

    println("3.2c ")
    jd.addConditionalDistributionColumn(PrA, { filter { A() } }).filter { A() || B() }.sumOf { PrA() }.print("  What is Pr(A = true|A = true ∨ B = true)?")
    jd.addConditionalDistributionColumn(PrB, { filter { B() } }).filter { A() || B() }.sumOf { PrB() }.print("  What is Pr(B = true|A = true ∨ B = true)?")

    println("\n3.2d Determine if the event B = true is conditionally independent of C = true given the event A = true ∨ B = true?  ANSWER YES")
    val PrAorB by column<Double>("Pr(.|A v B)")
    jd.addConditionalDistributionColumn(PrAorB, { filter { A() || B() } }).apply {
        filter { B() }.sumOf { PrAorB() }.print("  Pr(B|A v B)")
        filter { C() }.sumOf { PrAorB() }.print("  Pr(C|A v B)")
    }

}

private val raw: String
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