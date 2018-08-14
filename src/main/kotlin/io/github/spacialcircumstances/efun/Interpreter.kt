package io.github.spacialcircumstances.efun

import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.InterpreterConfig

class Interpreter(val interpreterConfig: InterpreterConfig) {
    private val iContext = interpreterConfig.createInterpreterContext()
    private val tContext = interpreterConfig.createTypeContext()

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
                    it.guessType(tContext)
                }
                ast?.forEach {
                    resultCallback(it.evaluate(iContext))
                }
            } catch (e: RuntimeError) {
                errorCallback(e)
            } catch (e: TypeError) {
                errorCallback(e)
            }
        }
    }
}