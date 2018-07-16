package io.github.spacialcircumstances.efun.interpreter

import io.github.spacialcircumstances.efun.expressions.AbstractExpression

class FFunction(val parameters: Map<String, FValueType>, val expressions: List<AbstractExpression>) {
    fun run(arguments: List<FValue>): FValue {
        return FValue(FValueType.Void, null)
    }
}