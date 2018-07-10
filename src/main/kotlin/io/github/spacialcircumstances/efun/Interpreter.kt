package io.github.spacialcircumstances.efun

import io.github.spacialcircumstances.efun.parser.parser

class Interpreter {
    fun interpret(code: String) {
        val tokens = tokenize(code)
        println(tokens.joinToString { it.type.toString() })
        val parsed = parser(tokens, ::parseLiteral)
        parsed.forEach { println(it.toString()) }
    }
}