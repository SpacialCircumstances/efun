package io.github.spacialcircumstances.efun.parser

class Parser<out R, T>(private val parse: (List<T>) -> Pair<R?, List<T>>) {
    fun run(input: List<T>): Pair<R?, List<T>> = parse(input)
}

fun<R, T> ret(value: R): Parser<R, T> =
    Parser {
        Pair(value, it)
    }

fun<R, T> oneWith(match: (T) -> Boolean, convert: (T) -> R): Parser<R, T> =
    Parser { input ->
        if (input.isEmpty()) Pair(null, input) else {
            val first = input.first()
            val rest = if (input.size > 1) input.subList(1, input.size) else emptyList()
            if (match(first)) {
                val result = convert(first)
                Pair(result, rest)
            } else {
                Pair(null, input)
            }
        }
    }

fun<T> one(match: (T) -> Boolean): Parser<T, T> =
    Parser { input ->
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

fun<R1, R2, T> Parser<R1, T>.bind(function: (R1) -> Parser<R2, T>): Parser<R2, T> =
    Parser { input: List<T> ->
        val (result, rem) = this.run(input)
        if (result == null) {
            Pair(null, input)
        } else {
            val np = function(result)
            np.run(rem)
        }
    }

fun<R1, R2, T> Parser<R1, T>.map(transform: (R1) -> R2): Parser<R2, T> =
    bind {
        ret<R2, T>(transform(it))
    }

fun<R1, R2, T> apply(parser1: Parser<(R1) -> R2, T>, parser2: Parser<R1, T>): Parser<R2, T> =
    parser1.bind {
        parser2.bind { it2 ->
            ret<R2, T>(it(it2))
        }
    }

fun<R1, R2, R3, T> lift2(f: (R1) -> (R2) -> R3, p1: Parser<R1, T>, p2: Parser<R2, T>): Parser<R3, T> {
    val rf = ret<(R1) -> (R2) -> R3, T>(f)
    val i1 = apply(rf, p1)
    return apply(i1, p2)
}