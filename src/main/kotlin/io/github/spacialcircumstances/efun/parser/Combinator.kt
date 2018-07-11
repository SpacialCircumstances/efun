package io.github.spacialcircumstances.efun.parser

fun<T, R> Parser<T, R>.optional(): Parser<T, R> {
    return Parser {
        val (result, rem) = this.run(it)
        if (result == null) {
            Pair(emptyList(), rem)
        } else {
            Pair(result, rem)
        }
    }
}

fun<T, R> Parser<T, R>.orElse(other: Parser<T, R>): Parser<T, R> {
    return Parser {
        val (firstResult, rem1) = this.run(it)
        if (firstResult == null) {
            other.run(rem1)
        } else {
            Pair(firstResult, rem1)
        }
    }
}

fun<T, R> Parser<T, R>.andThen(second: Parser<T, R>): Parser<T, R> {
    return Parser {
        val (firstResult, firstRemaining) = this.run(it)
        if (firstResult == null) {
            Pair(null, firstRemaining)
        } else {
            val (secondResult, secondRemaining) = second.run(firstRemaining)
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