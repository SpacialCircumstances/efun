package io.github.spacialcircumstances.efun

import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.InterpreterContext

class Interpreter {
    val context = InterpreterContext(null)

    fun interpret(code: String, resultCallback: (FValue) -> Unit = {}) {
        val tokens = tokenize(code)
        val parseResult = programParser.run(tokens)
        val ast = parseResult.first
        if (parseResult.second.isNotEmpty()) {
            val firstUnparsedToken = parseResult.second.first()
            println("Parser error in line: ${firstUnparsedToken.line} with Token: ${firstUnparsedToken.type}")
        } else {
            ast?.forEach {
                resultCallback(it.evaluate(context))
            }
        }
    }
}