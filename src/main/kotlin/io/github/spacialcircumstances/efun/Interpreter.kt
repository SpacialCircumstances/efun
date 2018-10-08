package io.github.spacialcircumstances.efun

import io.github.spacialcircumstances.efun.expressions.AbstractExpression
import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.InterpreterContext
import io.github.spacialcircumstances.efun.interpreter.TypesContext
import io.github.spacialcircumstances.efun.performance.PerformanceMetric
import io.github.spacialcircumstances.efun.performance.Stopwatch
import java.time.Duration

class ParserError(val message: String)

data class InterpreterState(val interpreterContext: InterpreterContext,
                            val typesContext: TypesContext,
                            val parserErrorHandler: (ParserError) -> Unit,
                            val typeErrorHandler: (TypeError) -> Unit,
                            val runtimeErrorHandler: (RuntimeError) -> Unit,
                            val resultHandler: (FValue) -> Unit)

fun parse(code: String, state: InterpreterState): List<AbstractExpression>? {
    val tokens = tokenize(code)
    val parseResult = programParser.run(tokens)
    val ast = parseResult.first
    return if (parseResult.second.isNotEmpty()) {
        val firstUnparsedToken = parseResult.second.first()
        val err = ParserError("Parser error in line: ${firstUnparsedToken.line} with Token: ${firstUnparsedToken.type}")
        state.parserErrorHandler(err)
        null
    } else ast
}

fun typeCheck(ast: List<AbstractExpression>, state: InterpreterState): List<AbstractExpression>? {
    return try {
        ast.forEach {
            it.guessType(state.typesContext)
        }
        ast
    } catch (e: TypeError) {
        state.typeErrorHandler(e)
        null
    }
}

fun interpret(ast: List<AbstractExpression>, state: InterpreterState): InterpreterState? {
    return try {
        ast.forEach {
            val result = it.evaluate(state.interpreterContext)
            state.resultHandler(result)
        }
        state
    } catch (e: RuntimeError) {
        state.runtimeErrorHandler(e)
        null
    }
}

interface IInterpreter {
    fun interpret(code: String): InterpreterState?
}

//Simple interpreter for running all steps
class Interpreter(private val initialState: InterpreterState): IInterpreter {
    override fun interpret(code: String): InterpreterState? {
        val parsedAst = parse(code, initialState)
        return if (parsedAst != null) {
            val checkedAst = typeCheck(parsedAst, initialState)
            if (checkedAst != null) {
                interpret(checkedAst, initialState)
                initialState
            } else null
        } else null
    }
}

class PerformanceMeasuringInterpreter(private val initialState: InterpreterState, private val performanceHandler: (PerformanceMetric) -> Unit): IInterpreter {
    override fun interpret(code: String): InterpreterState? {
        val sw = Stopwatch()
        sw.start()
        val parsedAst = parse(code, initialState)
        return if (parsedAst != null) {
            sw.stop()
            performanceHandler(PerformanceMetric("Parser", Duration.ofNanos(sw.elapsedTimeNanoseconds)))
            sw.start()
            val checkedAst = typeCheck(parsedAst, initialState)
            if (checkedAst != null) {
                sw.stop()
                performanceHandler(PerformanceMetric("Typechecker", Duration.ofNanos(sw.elapsedTimeNanoseconds)))
                sw.start()
                interpret(checkedAst, initialState)
                sw.stop()
                performanceHandler(PerformanceMetric("Interpreter", Duration.ofNanos(sw.elapsedTimeNanoseconds)))
                initialState
            } else null
        } else null
    }
}