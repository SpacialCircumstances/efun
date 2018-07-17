package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.FFunction
import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.InterpreterContext

class FunctionCallExpression(private val functionExpression: AbstractExpression, private val args: List<AbstractExpression>): AbstractExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        val function = functionExpression.evaluate(context).value as FFunction
        return function.run(args.map { it.evaluate(context) })
    }
}