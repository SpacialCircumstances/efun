package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

class BlockExpression(val parameters: List<Pair<String, FType<*>>>, val body: List<AbstractExpression>): AbstractExpression() {
    override fun guessType(context: TypeContext): FType<*> {
        val context = context.copy()
        parameters.forEach {
            context[it.first] = it.second
        }
        val returnType = body.map { it.guessType(context) }.last()
        return createFunctionType(parameters.map { it.second }, returnType)
    }

    override fun evaluate(context: InterpreterContext): FValue {
        val function = createFunction(parameters, body, context)
        return FValue(TFunction, function)
    }
}