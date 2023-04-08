package homework

import kotlin.math.pow

fun <T> T.print(s: String? = null): T = apply {
    println((s?.let { "$it " } ?: "") + toString())
}

fun <E> List<E>.printList(s: String? = null): List<E> = apply {
    onEachIndexed { x, it -> println((s?.let { "$it " } ?: "") + "$x -> $it") }
}

fun Int.pow(i: Int): Int = toDouble().pow(i).toInt()