package io.github.spacialcircumstances.efun.interpreter

import io.github.spacialcircumstances.efun.expressions.AbstractExpression

class FFunction(val parameters: List<Pair<String, FType<*>>>, val expressions: List<AbstractExpression>, val environment: InterpreterContext) {
    fun run(arguments: List<FValue>): FValue {
        if (arguments.size != parameters.size) throw IllegalStateException("Arguments count of ${arguments.size} does not match intended of ${parameters.size}")
        val newEnv = environment.copy()
        parameters.forEachIndexed { index, s ->
            val arg = arguments[index]
            if (arg.type != s.second) throw IllegalStateException("Type ${s.second} expected as parameter $index, but ${arg.type} given")
            newEnv[s.first] = arg
        }
        return expressions.map { it.evaluate(newEnv) }.last()
    }
}