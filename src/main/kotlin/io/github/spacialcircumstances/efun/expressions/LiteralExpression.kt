package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.FType
import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.InterpreterContext
import io.github.spacialcircumstances.efun.interpreter.TypeContext

class LiteralExpression(private val literal: FValue): AbstractExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        return literal
    }

    override fun guessType(context: TypeContext): FType<*> {
        return literal.type
    }
}