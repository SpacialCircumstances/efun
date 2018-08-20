package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

class RecLetExpression(val name: String, val retType: PlaceholderType, val expr: BlockExpression): AbstractExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        val result = expr.evaluate(context)
        context[name] = result
        return result
    }

    override fun guessType(context: TypeContext): FType<*> {
        val resolvedParamTypes = expr.parameters.map {
            it.second.resolveType(context)
        }
        val finalFunctionType = createFunctionType(resolvedParamTypes, retType.resolveType(context))
        context[name] = finalFunctionType
        expr.guessType(context)
        return TVoid
    }
}