package dataframe

import org.jetbrains.kotlinx.dataframe.api.column

val World by column<String>()
val Pr by column<Double>("Pr(.)")
val A by column<Boolean>()
val B by column<Boolean>()
val C by column<Boolean>()

val PrAorB by column<Double>("Pr(.|A v B)")
val PrA by column<Double>("Pr(.|A)")
val PrB by column<Double>("Pr(.|B)")
val PrC by column<Double>("Pr(.|C)")

val D by column<Boolean>()
val T by column<Boolean>()
val PrD by column<Double>("Pr(.|D)")
val PrNotD by column<Double>("Pr(.|!D)")