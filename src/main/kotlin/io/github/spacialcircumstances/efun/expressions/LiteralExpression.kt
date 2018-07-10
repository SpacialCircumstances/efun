package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.InterpreterContext

class LiteralExpression(private val literal: FValue): AbstractExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        return literal
    }
}