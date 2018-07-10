package io.github.spacialcircumstances.efun.parser

fun<T, R> parser(tokens: List<T>, function:  (List<T>) -> Pair<R?, List<T>>): List<R> {
    val result = function(tokens)
    if (result.second.isEmpty()) {
        result.first?.let {
            return listOf(it)
        } ?: return emptyList()
    } else {
        val first = result.first
        if (first == null) {
            TODO("call next parser function")
        } else {
            return listOf(first) + parser(result.second, function)
        }
    }
}