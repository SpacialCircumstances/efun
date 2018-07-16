package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.InterpreterContext

class VariableExpression(private val name: String): AbstractExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        return context[name] ?: throw IllegalStateException("Value $name does not exist in this scope")
    }
}