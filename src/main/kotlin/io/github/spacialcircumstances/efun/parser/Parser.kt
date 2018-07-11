package io.github.spacialcircumstances.efun.parser

class Parser<T, out R>(private val parseFunction: (List<T>) -> Pair<List<R>?, List<T>>) {
    fun run(input: List<T>): Pair<List<R>?, List<T>> = parseFunction(input)
}

class Finder<T>(val findFunction: (List<T>) -> Pair<List<T>?, List<T>>)

fun<T> one(match: (T) -> Boolean): Finder<T> {
    return Finder { input ->
        if (input.isEmpty()) Pair(null, input) else {
            val first = input.first()
            val rest = if (input.size > 1) input.subList(1, input.size) else emptyList()
            if (match(first)) {
                Pair(listOf(first), rest)
            } else {
                Pair(null, input)
            }
        }
    }
}

fun<T, R> Finder<T>.with(convert: (List<T>) -> List<R>): Parser<T, R> {
    return Parser {
        val (result, rem) = this.findFunction(it)
        if (result == null) {
            Pair(null, rem)
        } else {
            val final = convert(result)
            Pair(final, rem)
        }
    }
}

fun<T, R> Finder<T>.map(convert: (T) ->R): Parser<T, R> {
    return Parser {
        val (result, rem) = this.findFunction(it)
        if (result == null) {
            Pair(null, rem)
        } else {
            val final = result.map(convert)
            Pair(final, rem)
        }
    }
}

fun<T, R> Finder<T>.mapSingle(convert: (List<T>) -> R): Parser<T, R> {
    return Parser {
        val (result, rem) = this.findFunction(it)
        if (result == null) {
            Pair(null, rem)
        } else {
            val final = convert(result)
            Pair(listOf(final), rem)
        }
    }
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
                Pair(null, input)
            }
        }
    })
}