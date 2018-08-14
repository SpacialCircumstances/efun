package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.RuntimeError
import io.github.spacialcircumstances.efun.TypeError
import io.github.spacialcircumstances.efun.interpreter.FType
import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.InterpreterContext
import io.github.spacialcircumstances.efun.interpreter.TypeContext

class VariableExpression(private val name: String): AbstractExpression() {
    override fun guessType(context: TypeContext): FType<*> {
        return context[name] ?: throw TypeError("Variable $name does not exist in this scope")
    }

    override fun evaluate(context: InterpreterContext): FValue {
        return context[name] ?: throw RuntimeError("Value $name does not exist in this scope")
    }
}