package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

class DebugExpression(private val expression: AbstractExpression): AbstractExpression() {
    override fun guessType(context: TypesContext): FType<*> {
        expression.guessType(context)
        return TVoid
    }

    override fun evaluate(context: InterpreterContext): FValue {
        println(expression.evaluate(context).value)
        return FValue(TVoid, null)
    }
}