package io.github.spacialcircumstances.efun

import io.github.spacialcircumstances.efun.expressions.AbstractExpression
import io.github.spacialcircumstances.efun.interpreter.FType
import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.InterpreterContext
import io.github.spacialcircumstances.efun.interpreter.TypeContext

class ParserError(val message: String)

data class InterpreterState(val interpreterContext: InterpreterContext,
                            val typeContext: TypeContext,
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
            it.guessType(state.typeContext)
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

//Simple interpreter for running all steps
class Interpreter(private val initialState: InterpreterState) {
    fun interpret(code: String): InterpreterState? {
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