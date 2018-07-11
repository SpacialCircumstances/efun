package io.github.spacialcircumstances.efun.parser

class Parser<T, out R>(private val parseFunction: (List<T>) -> Pair<List<R>?, List<T>>) {
    fun run(input: List<T>): Pair<List<R>?, List<T>> = parseFunction(input)
}

fun<T, R> one(match: (T) -> Boolean, convert: (T) -> R): Parser<T, R> {
    return Parser({ input ->
        if (input.isEmpty()) Pair(null, input) else {
            val first = input.first()
            val rest = if (input.size > 1) input.subList(1, input.size) else emptyList()
            if (match(first)) {
                val result = convert(first)
                Pair(listOf(result), rest)
            } else {
                Pair(null, rest)
            }
        }
    })
}