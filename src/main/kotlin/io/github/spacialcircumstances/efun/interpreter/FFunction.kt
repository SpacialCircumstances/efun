package io.github.spacialcircumstances.efun.interpreter

import io.github.spacialcircumstances.efun.expressions.AbstractExpression

class FFunction(val parameters: Map<String, FValueType>, val expressions: List<AbstractExpression>, val environment: InterpreterContext) {
    fun run(arguments: List<FValue>): FValue {
        val newEnv = environment.copy()
        parameters.keys.forEachIndexed { index, s ->
            val arg = arguments[index]
            newEnv[s] = arg
        }
        return expressions.map { it.evaluate(newEnv) }.last()
    }
}