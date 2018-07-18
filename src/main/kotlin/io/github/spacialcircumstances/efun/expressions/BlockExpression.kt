package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

class BlockExpression(val parameters: List<Pair<String, FType<*>>>, val body: List<AbstractExpression>): AbstractExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        return FValue(TFunction, FFunction(parameters, body, context))
    }
}