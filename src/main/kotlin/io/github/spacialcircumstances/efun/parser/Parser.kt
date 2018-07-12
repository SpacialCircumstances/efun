package io.github.spacialcircumstances.efun.parser

class Parser<out R, T>(private val parse: (List<T>) -> Pair<R?, List<T>>) {
    fun run(input: List<T>): Pair<R?, List<T>> = parse(input)
}

fun<R, T> ret(value: R): Parser<R, T> {
    return Parser {
        Pair(value, it)
    }
}

fun<T> one(match: (T) -> Boolean): Parser<T, T> {
    return Parser { input ->
        if (input.isEmpty()) Pair(null, input) else {
            val first = input.first()
            val rest = if (input.size > 1) input.subList(1, input.size) else emptyList()
            if (match(first)) {
                Pair(first, rest)
            } else {
                Pair(null, input)
            }
        }
    }
}

fun<R1, R2, T> Parser<R1, T>.bind(function: (R1) -> Parser<R2, T>): Parser<R2, T> {
    return Parser { input: List<T> ->
        val (result, rem) = this.run(input)
        if (result == null) {
            Pair(null, input)
        } else {
            val np = function(result)
            np.run(rem)
        }
    }
}

fun<R1, R2, T> Parser<R1, T>.map(transform: (R1) -> R2): Parser<R2, T> {
    return bind {
        ret<R2, T>(transform(it))
    }
}

fun<R1, R2, T> apply(parser1: Parser<(R1) -> R2, T>, parser2: Parser<R1, T>): Parser<R2, T> =
    parser1.bind {
        parser2.bind { it2 ->
            ret<R2, T>(it(it2))
        }
    }