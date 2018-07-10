package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.InterpreterContext

class GroupingExpression(private val expression: AbstractExpression): AbstractExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        return expression.evaluate(context)
    }
}