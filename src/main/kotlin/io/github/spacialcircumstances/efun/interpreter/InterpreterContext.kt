package io.github.spacialcircumstances.efun.interpreter

class InterpreterContext(val parent: InterpreterContext?) {
    private val variables = mutableMapOf<String, FValue>()

    operator fun get(key: String): FValue? {
        return variables[key] ?: parent?.get(key)
    }

    operator fun set(key: String, variable: FValue) {
        if (variables[key] != null) {
            throw IllegalStateException("Values must be immutable")
        } else {
            variables[key] = variable
        }
    }

    fun copy(): InterpreterContext {
        val newCtx = InterpreterContext(parent)
        newCtx.variables.putAll(variables)
        return newCtx
    }
}