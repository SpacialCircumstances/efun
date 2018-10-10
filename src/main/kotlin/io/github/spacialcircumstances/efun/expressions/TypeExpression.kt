package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

class TypeExpression(val name: String, private val typeExpr: AbstractTypeExpression): AbstractExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        typeExpr.evaluate(context)
        return FValue(TVoid, null)
    }

    override fun guessType(context: TypesContext): FType<*> {
        val type = typeExpr.type(context)
        context.importChildType(type)
        return type
    }
}