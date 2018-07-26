package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.FunctionPointer
import io.github.spacialcircumstances.efun.interpreter.InterpreterContext
import io.github.spacialcircumstances.efun.interpreter.runWhileFunction

class FunctionCallExpression(private val functionExpression: AbstractExpression, private val args: List<AbstractExpression>): AbstractExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        val function = functionExpression.evaluate(context).value as FunctionPointer
        return runWhileFunction(function, args.map { it.evaluate(context) })
    }
}