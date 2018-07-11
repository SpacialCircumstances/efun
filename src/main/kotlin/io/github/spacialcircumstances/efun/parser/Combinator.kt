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

fun<T, R> Parser<T, R>.many(): Parser<T, R> {
    return Parser {
        val results = mutableListOf<R>()
        var lastRem = it
        while(true) {
            val (result, rem) = this.run(lastRem)
            if (result == null) {
                break
            } else {
                results.addAll(result)
                lastRem = rem
            }
        }
        Pair(results, lastRem)
    }
}