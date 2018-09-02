package io.github.spacialcircumstances.efun

import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.InterpreterConfig
import io.github.spacialcircumstances.efun.performance.Stopwatch

class Interpreter(private val interpreterConfig: InterpreterConfig) {
    private val iContext = interpreterConfig.createInterpreterContext()
    private val tContext = interpreterConfig.createTypeContext()

    fun interpret(code: String, resultCallback: (FValue) -> Unit = {}, errorCallback: (Throwable) -> Unit = { throw IllegalStateException(it) }, executionLogCallback: (String, Long) -> Unit = { _, _ -> }) {
        val sw = Stopwatch()
        sw.start()
        val tokens = tokenize(code)
        val parseResult = programParser.run(tokens)
        val ast = parseResult.first
        executionLogCallback("Parsing", sw.elapsedTimeNanoseconds)
        sw.reset()
        if (parseResult.second.isNotEmpty()) {
            val firstUnparsedToken = parseResult.second.first()
            println("Parser error in line: ${firstUnparsedToken.line} with Token: ${firstUnparsedToken.type}")
        } else {
            try {
                ast?.forEach {
                    it.guessType(tContext)
                }
                executionLogCallback("Typechecking", sw.elapsedTimeNanoseconds)
                sw.reset()
                ast?.forEach {
                    resultCallback(it.evaluate(iContext))
                }
                executionLogCallback("Executing", sw.elapsedTimeNanoseconds)
                sw.stop()
            } catch (e: RuntimeError) {
                errorCallback(e)
            } catch (e: TypeError) {
                errorCallback(e)
            }
        }
    }
}