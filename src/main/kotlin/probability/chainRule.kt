package probability


fun Probability.chainRule(): List<Probability> = List(query.size) { index ->
    Probability(listOf(query[index]), query.subList(index + 1, query.size))
}
