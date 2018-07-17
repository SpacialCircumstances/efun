package io.github.spacialcircumstances.efun

import io.github.spacialcircumstances.efun.interpreter.InterpreterContext

class Interpreter {
    val context = InterpreterContext(null)

    fun interpret(code: String) {
        val tokens = tokenize(code)
        println(tokens.joinToString { it.type.toString() })
        val parseResult = programParser.run(tokens)
        val ast = parseResult.first
        ast?.forEach {
            println(it.evaluate(context))
        }
    }
}