package io.github.spacialcircumstances.efun.interpreter

class InterpreterConfig(val externalValues: Map<String, FValue>) {
    fun createInterpreterContext(): InterpreterContext {
        val context = InterpreterContext(null)
        externalValues.forEach {
            context[it.key] = it.value
        }
        return context
    }

    fun createTypeContext(): TypeContext {
        val context = TypeContext(null)
        externalValues.forEach {
            context[it.key] = it.value.type
        }
        return context
    }
}