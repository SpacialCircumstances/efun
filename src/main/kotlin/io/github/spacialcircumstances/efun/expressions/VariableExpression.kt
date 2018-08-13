package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.FType
import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.InterpreterContext
import io.github.spacialcircumstances.efun.interpreter.TypeContext

class VariableExpression(private val name: String): AbstractExpression() {
    override fun guessType(context: TypeContext): FType<*> {
        return context[name] ?: throw IllegalStateException("Variable $name does not exist in this scope")
    }

    override fun evaluate(context: InterpreterContext): FValue {
        return context[name] ?: throw IllegalStateException("Value $name does not exist in this scope")
    }
}