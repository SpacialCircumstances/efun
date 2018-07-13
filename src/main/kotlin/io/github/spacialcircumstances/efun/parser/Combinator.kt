package io.github.spacialcircumstances.efun.parser

fun<R1, R2, T> Parser<R1, T>.andThen(p2: Parser<R2, T>): Parser<Pair<R1, R2>, T> =
    Parser { input ->
        val (result, rem) = this.run(input)
        if (result == null) {
            Pair(null, input)
        } else {
            val (secondResult, rem2) = p2.run(rem)
            if (secondResult == null) {
                Pair(null, input)
            } else {
                Pair(Pair(result, secondResult), rem2)
            }
        }
    }