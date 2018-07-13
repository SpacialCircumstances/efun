package io.github.spacialcircumstances.efun

class Interpreter {
    fun interpret(code: String) {
        val tokens = tokenize(code)
        println(tokens.joinToString { it.type.toString() })
        val parseResult = programParser.run(tokens)
        val ast = parseResult.first
        println(ast?.size)
    }
}