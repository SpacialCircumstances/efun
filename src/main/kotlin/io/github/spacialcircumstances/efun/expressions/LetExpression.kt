package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.Token
import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.FValueType
import io.github.spacialcircumstances.efun.interpreter.InterpreterContext

class LetExpression(private val expression: AbstractExpression, private val name: Token): AbstractExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        return FValue(FValueType.Void, null)
    }
}