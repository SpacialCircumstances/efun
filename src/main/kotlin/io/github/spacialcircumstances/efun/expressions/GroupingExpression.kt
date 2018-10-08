package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.FType
import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.InterpreterContext
import io.github.spacialcircumstances.efun.interpreter.TypesContext

class GroupingExpression(private val expression: AbstractExpression): AbstractExpression() {
    override fun guessType(context: TypesContext): FType<*> {
        return expression.guessType(context)
    }

    override fun evaluate(context: InterpreterContext): FValue {
        return expression.evaluate(context)
    }
}