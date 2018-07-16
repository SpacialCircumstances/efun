package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.InterpreterContext

class LetExpression(private val expression: AbstractExpression, private val name: String): AbstractExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        val result = expression.evaluate(context)
        context[name] = result
        return result
    }
}