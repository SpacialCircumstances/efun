package io.github.spacialcircumstances.efun

import io.github.spacialcircumstances.efun.parser.many

class Interpreter {
    fun interpret(code: String) {
        val tokens = tokenize(code)
        println(tokens.joinToString { it.type.toString() })
        val literalsParser = literalParser.many()
        val (parsed, _) = literalsParser.run(tokens)
        parsed?.forEach { println(it) }
    }
}