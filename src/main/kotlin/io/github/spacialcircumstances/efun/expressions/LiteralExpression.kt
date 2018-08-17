package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

class LiteralExpression(private val parserLiteral: Pair<PlaceholderType, Any>): AbstractExpression() {
    private var literal: FValue? = null
    override fun evaluate(context: InterpreterContext): FValue {
        return literal!!
    }

    override fun guessType(context: TypeContext): FType<*> {
        literal = FValue(parserLiteral.first.resolveType(context), parserLiteral.second)
        return literal!!.type
    }
}