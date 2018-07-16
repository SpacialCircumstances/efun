package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.FFunction
import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.FValueType
import io.github.spacialcircumstances.efun.interpreter.InterpreterContext

class BlockExpression(val parameters: Map<String, FValueType>, val body: List<AbstractExpression>): AbstractExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        return FValue(FValueType.Function, FFunction(parameters, body))
    }
}