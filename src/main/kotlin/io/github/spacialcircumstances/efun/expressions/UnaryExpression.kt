package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.FValueType
import io.github.spacialcircumstances.efun.interpreter.InterpreterContext

class UnaryExpression(private val expression: AbstractExpression, private val operator: String): AbstractExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        return FValue(FValueType.Void, null)
    }
}