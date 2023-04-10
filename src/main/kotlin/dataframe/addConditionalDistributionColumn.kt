package dataframe

import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.*
import org.jetbrains.kotlinx.dataframe.columns.*
import kotlin.math.absoluteValue

public fun <T> DataFrame<T>.addConditionalDistributionColumn(newCol: ColumnAccessor<Double>, f: DataFrame<T>. () -> DataFrame<T>): DataFrame<T> {
    val newSum = f.invoke(this).sumOf { Pr() }.toDouble()
    val vals = f.invoke(this).associate { World() to Pr() / newSum }
    return add(DataColumn.create<Double>(newCol.name(), map { vals[World()] ?: 0.toDouble() })).also {
        if (it.sumOf { newCol() }.minus(1.toDouble()).absoluteValue > 0.00000000000001.toDouble()) throw Exception("${newCol.name()} sum is ${it.sumOf { newCol() }}")
    }
}