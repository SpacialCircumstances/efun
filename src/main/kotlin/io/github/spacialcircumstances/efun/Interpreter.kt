package io.github.spacialcircumstances.efun

class Interpreter {
    fun interpret(code: String) {
        val tokens = tokenize(code)
        println(tokens.joinToString { it.type.toString() })
        val (parsed, rem) = parseLiteral(tokens)
        parsed?.forEach {
            println(it.toString())
        }
    }
}