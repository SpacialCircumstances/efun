package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

class MutableLetExpression(private val expression: AbstractExpression, private val name: String): AbstractExpression() {
    override fun guessType(context: TypesContext): FType<*> {
        val type = expression.guessType(context)
        context.registerPublicType(name, MutableType(type))
        return TVoid
    }

    override fun evaluate(context: InterpreterContext): FValue {
        val result = expression.evaluate(context)
        context.setMutable(name, result)
        return result
    }
}