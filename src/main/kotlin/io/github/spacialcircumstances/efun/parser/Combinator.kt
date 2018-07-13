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

fun<R, T> Parser<R, T>.orElse(other: Parser<R, T>): Parser<R, T> =
    Parser { input ->
        val (result, rem) = this.run(input)
        if (result == null) {
            val (secondResult, rem2) = other.run(input)
            Pair(secondResult, rem2)
        } else {
            Pair(result, rem)
        }
    }

fun<R, T> choice(vararg parsers: Parser<R, T>): Parser<R, T> {
    return parsers.reduce { p1, p2 ->
        p1.orElse(p2)
    }
}

fun<R, T> sequence(vararg parsers: Parser<R, T>): Parser<List<R>, T> {
    return Parser {
        if (it.isEmpty()) {
            Pair(emptyList<R>(), it)
        }
        val results: MutableList<R> = mutableListOf()
        var success = true
        var current = it
        for (parser in parsers) {
            val (r, rem) = parser.run(current)
            if (r == null) {
                success = false
                break
            } else {
                results.add(r)
                current = rem
            }
        }
        if (success) Pair(results, current) else Pair(null, it)
    }
}

fun<R, T> Parser<R, T>.many(): Parser<List<R>, T> {
    return Parser {
        var remains = it
        val results = mutableListOf<R>()
        while (true) {
            val (r, rem) = this.run(remains)
            if (r == null) {
                break
            } else {
                results.add(r)
                remains = rem
            }
        }
        Pair(results, remains)
    }
}

fun<R, T> Parser<R, T>.moreThan1(): Parser<List<R>, T> {
    return Parser {
        var remains = it
        val results = mutableListOf<R>()
        while (true) {
            val (r, rem) = this.run(remains)
            if (r == null) {
                break
            } else {
                results.add(r)
                remains = rem
            }
        }
        if (results.isEmpty()) {
            Pair(null, it)
        } else {
            Pair(results, remains)
        }
    }
}

fun<R1, R2, T> takeLeft(take: Parser<R1, T>, ignore: Parser<R2, T>): Parser<R1, T> =
    take.andThen(ignore).map { it.first }

fun<R1, R2, T> takeRight(ignore: Parser<R1, T>, take: Parser<R2, T>): Parser<R2, T> =
    ignore.andThen(take).map { it.second }

fun<R1, R2, T> Parser<R1, T>.andIgnoreResult(ignore: Parser<R2, T>): Parser<R1, T> {
    return takeLeft(this, ignore)
}