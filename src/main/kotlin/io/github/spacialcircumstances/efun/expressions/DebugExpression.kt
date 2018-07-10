package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.FValueType
import io.github.spacialcircumstances.efun.interpreter.InterpreterContext

class DebugExpression(private val expression: AbstractExpression): AbstractExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        println(expression.evaluate(context))
        return FValue(FValueType.Void, null)
    }
}