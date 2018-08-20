package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

class TypeExpression(val name: String, val typeExpr: AbstractExpression): AbstractExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        typeExpr.evaluate(context)
        return FValue(TVoid, null)
    }

    override fun guessType(context: TypeContext): FType<*> {
        val type = typeExpr.guessType(context)
        context.registerType(name, type)
        return type
    }
}