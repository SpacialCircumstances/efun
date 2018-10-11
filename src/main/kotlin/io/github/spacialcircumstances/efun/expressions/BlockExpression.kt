package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

class BlockExpression(val parameters: List<Pair<String, PlaceholderType>>, private val body: List<AbstractExpression>): AbstractExpression() {
    private val parameterNames = parameters.map { it.first }
    var type: FunctionType? = null

    override fun guessType(context: TypesContext): FType<*> {
        val context = TypesContext(context, emptyMap()) //Can get default type mappings via parent
        val actualTypes = parameters.map { Pair(it.first, it.second.resolveType(context)) }
        actualTypes.forEach {
            context.registerPrivateType(it.first, it.second)
        }
        val returnType = body.asSequence().map { it.guessType(context) }.last()
        type = createFunctionType(actualTypes.map { it.second }, returnType)
        return type!!
    }

    override fun evaluate(context: InterpreterContext): FValue {
        val function = createFunction(parameterNames, type!!, body, context)
        return FValue(type!!, function)
    }
}