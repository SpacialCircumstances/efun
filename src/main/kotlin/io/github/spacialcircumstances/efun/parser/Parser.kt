package io.github.spacialcircumstances.efun.parser

fun<T, R> orElse(first: (List<T>) -> Pair<List<R>?, List<T>>, second: (List<T>) -> Pair<List<R>?, List<T>>): (List<T>) -> Pair<List<R>?, List<T>> {
    return {
        val firstResult = first(it)
        if (firstResult.first == null) {
            second(firstResult.second)
        } else {
            firstResult
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

fun<T, R> untilNull(function: (List<T>) -> Pair<List<R>?, List<T>>): (List<T>)-> Pair<List<R>?, List<T>> {
    return {
        val (result, rem) = function(it)
        if (result == null) {
            Pair(null, rem)
        } else {
            val next = untilNull(function)
            val (nextResult, nextRem) = next(rem)
            if (nextResult == null) {
                Pair(result, rem)
            } else {
                Pair(result + nextResult, nextRem)
            }
        }
    }
}