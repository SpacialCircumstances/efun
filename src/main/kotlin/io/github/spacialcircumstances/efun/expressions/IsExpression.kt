package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

class IsExpression(val expression: AbstractExpression, val typeExpr: PlaceholderType): AbstractExpression() {
    var type: FType<*>? = null
    override fun evaluate(context: InterpreterContext): FValue {
        val value = expression.evaluate(context)
        val eq = value.type == type!!
        return FValue(TBool, eq)
    }

    override fun guessType(context: TypesContext): FType<*> {
        type = typeExpr.resolveType(context)
        expression.guessType(context)
        return TBool
    }
}