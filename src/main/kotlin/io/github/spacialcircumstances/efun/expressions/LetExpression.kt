package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

class LetExpression(private val expression: AbstractExpression, private val name: String): AbstractExpression() {
    override fun guessType(context: TypeContext): FType<*> {
        context[name] = expression.guessType(context)
        return TVoid
    }

    override fun evaluate(context: InterpreterContext): FValue {
        val result = expression.evaluate(context)
        context[name] = result
        return result
    }
}