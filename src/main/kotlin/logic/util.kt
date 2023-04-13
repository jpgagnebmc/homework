package logic

import kotlin.math.pow

fun <T> T.print(message: String? = null): T = apply {
    println((message?.let { "$it " } ?: "") + toString())
}

fun <E> List<E>.printList(message: String? = null): List<E> = apply {
    onEachIndexed { x, it -> println((message?.let { "$it " } ?: "") + "$x -> $it") }
}

fun Int.pow(i: Int): Int = toDouble().pow(i).toInt()


fun <T> allPermutations(set: Set<T>): Set<List<T>> {
    if (set.isEmpty()) return emptySet()

    fun <T> _allPermutations(list: List<T>): Set<List<T>> {
        if (list.isEmpty()) return setOf(emptyList())

        val result: MutableSet<List<T>> = mutableSetOf()
        for (i in list.indices) {
            _allPermutations(list - list[i]).forEach { item ->
                result.add(item + list[i])
            }
        }
        return result
    }

    return _allPermutations(set.toList())
}

val String.braced: String
    get() = "{$this}"
