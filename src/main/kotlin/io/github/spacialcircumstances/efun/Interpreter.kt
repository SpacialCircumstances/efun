package io.github.spacialcircumstances.efun

import io.github.spacialcircumstances.efun.parser.untilNull

class Interpreter {
    fun interpret(code: String) {
        val tokens = tokenize(code)
        println(tokens.joinToString { it.type.toString() })
        val (parsed, _) = untilNull(::parseLiteral)(tokens)
        parsed?.forEach {
            println(it.toString())
        }
    }
}