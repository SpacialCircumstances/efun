package io.github.spacialcircumstances.efun

import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.InterpreterContext
import io.github.spacialcircumstances.efun.interpreter.TypeContext

class Interpreter {
    val context = InterpreterContext(null)
    val tc = TypeContext(null)

    fun interpret(code: String, resultCallback: (FValue) -> Unit = {}, errorCallback: (Throwable) -> Unit = { throw IllegalStateException(it) }) {
        val tokens = tokenize(code)
        val parseResult = programParser.run(tokens)
        val ast = parseResult.first
        if (parseResult.second.isNotEmpty()) {
            val firstUnparsedToken = parseResult.second.first()
            println("Parser error in line: ${firstUnparsedToken.line} with Token: ${firstUnparsedToken.type}")
        } else {
            try {
                ast?.forEach {
                    it.guessType(tc)
                }
                ast?.forEach {
                    resultCallback(it.evaluate(context))
                }
            } catch (e: RuntimeError) {
                errorCallback(e)
            } catch (e: TypeError) {
                errorCallback(e)
            }
        }
    }
}