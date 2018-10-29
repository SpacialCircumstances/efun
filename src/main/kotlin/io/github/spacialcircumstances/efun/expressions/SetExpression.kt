package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

class SetExpression(private val expression: AbstractExpression, private val name: String): AbstractExpression() {
    override fun guessType(context: TypesContext): FType<*> {
        TODO()
        context.registerPublicType(name, expression.guessType(context))
        return TVoid
    }

    override fun evaluate(context: InterpreterContext): FValue {
        TODO()
        val result = expression.evaluate(context)
        context[name] = result
        return result
    }
}