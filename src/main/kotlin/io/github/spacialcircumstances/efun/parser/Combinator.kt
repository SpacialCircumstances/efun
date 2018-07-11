package io.github.spacialcircumstances.efun.parser

fun<T, R> optional(parser: Parser<T, R>): Parser<T, R> {
    return Parser {
        val (result, rem) = parser.run(it)
        if (result == null) {
            Pair(emptyList(), rem)
        } else {
            Pair(result, rem)
        }
    }
}

fun<T, R> orElse(parser: Parser<T, R>, other: Parser<T, R>): Parser<T, R> {
    return Parser {
        val (firstResult, rem1) = parser.run(it)
        if (firstResult == null) {
            other.run(rem1)
        } else {
            Pair(firstResult, rem1)
        }
    }
}

fun<T, R> andThen(first: (List<T>) -> Pair<List<R>?, List<T>>, second: (List<T>) -> Pair<List<R>?, List<T>>): (List<T>)-> Pair<List<R>?, List<T>> {
    return {
        val (firstResult, firstRemaining) = first(it)
        if (firstResult == null) {
            Pair(firstResult, firstRemaining)
        } else {
            val (secondResult, secondRemaining) = second(firstRemaining)
            if (secondResult == null) {
                Pair(null, secondRemaining)
            } else {
                Pair(firstResult + secondResult, secondRemaining)
            }
        }
    }
}

fun<T, R> many(function: (List<T>) -> Pair<List<R>?, List<T>>): (List<T>)-> Pair<List<R>?, List<T>> {
    return {
        val (result, rem) = function(it)
        if (result == null) {
            Pair(null, rem)
        } else {
            val next = many(function)
            val (nextResult, nextRem) = next(rem)
            if (nextResult == null) {
                Pair(result, rem)
            } else {
                Pair(result + nextResult, nextRem)
            }
        }
    }
}