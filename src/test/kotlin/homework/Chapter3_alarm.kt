package homework

import dataframe.Pr
import dataframe.World
import dataframe.addConditionalDistributionColumn
import formula.print
import org.jetbrains.kotlinx.dataframe.DataColumn
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.column
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf

import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.columns.ColumnAccessor
import kotlin.math.absoluteValue
import kotlin.test.Test


class Chapter3_alarm {
    private val Earthquake by column<Boolean>()
    private val Burglary by column<Boolean>()
    private val Alarm by column<Boolean>()

    @Test
    fun homework3_alarm() {


        val data = raw.lines().chunked(8)
        val PrAlarm by column<Double>("Pr(.|Alarm)")
        val PrBurglary by column<Double>("Pr(.|Burglary)")
        val PrEarthquake by column<Double>("Pr(.|Earthquake)")
        val PrAlarmEarthquake by column<Double>("Pr(.|Alarm ∧ Earthquake)")
        val PrAlarmNotEarthquake by column<Double>("Pr(.|Alarm ∧ ¬Earthquake)")
        val jd = dataFrameOf(World.name(), "Earthquake", "Burglary", "Alarm", "Pr(.)")
            .invoke(
                DataColumn.create<String>(World.name(), data[0]),
                DataColumn.create<Boolean>("Earthquake", data[1].map { it.toBooleanStrict() }),
                DataColumn.create<Boolean>("Burglary", data[2].map { it.toBooleanStrict() }),
                DataColumn.create<Boolean>("Alarm", data[3].map { it.toBooleanStrict() }),
                DataColumn.create<Double>("Pr(.)", data[4].map { it.toDouble() }),
            ).addConditionalDistributionColumn(PrAlarm, { filter { Alarm() } })
            .addConditionalDistributionColumn(PrBurglary, { filter { Burglary() } })
            .addConditionalDistributionColumn(PrEarthquake, { filter { Earthquake() } })
            .addConditionalDistributionColumn(PrAlarmEarthquake, { filter { Alarm() && Earthquake() } })
            .addConditionalDistributionColumn(PrAlarmNotEarthquake, { filter { Alarm() && !Earthquake() } })

        jd.filter { Earthquake() }.sumOf { Pr() }.print("Pr(Earthquake)")
        jd.filter { Earthquake() }.sumOf { PrBurglary() }.print("Pr(Earthquake|Burglary)")
        jd.filter { Burglary() }.sumOf { Pr() }.print("Pr(Burglary)")
        jd.filter { Burglary() }.sumOf { PrEarthquake() }.print("Pr(Burglary|Earthquake)")
        jd.filter { Burglary() }.sumOf { PrAlarm() }.print("Pr(Burglary|Alarm)")
        jd.filter { Burglary() }.sumOf { PrAlarmEarthquake() }.print("Pr(Burglary|Alarm ∧ Earthquake)")
        jd.filter { Alarm() }.sumOf { Pr() }.print("Pr(Alarm)")
        jd.filter { Alarm() }.sumOf { PrBurglary() }.print("Pr(Alarm|Burglary)")
        jd.filter { Alarm() }.sumOf { PrEarthquake() }.print("Pr(Alarm|Earthquake)")
        jd.filter { Earthquake() }.sumOf { PrAlarm() }.print("Pr(Earthquake|Alarm)")
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
.0190
.0010
.0560
.0240
.1620
.0180
.0072
.7128        
    """.trimIndent()
