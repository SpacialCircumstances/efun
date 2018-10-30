package io.github.spacialcircumstances.efun.interpreter

import io.github.spacialcircumstances.efun.RuntimeError

class InterpreterContext(private val parent: InterpreterContext?): IFValueStore {
    val variables = mutableMapOf<String, ValueSlot>()

    override operator fun get(key: String): ValueSlot? {
        return variables[key] ?: parent?.get(key)
    }

    operator fun set(key: String, variable: FValue) {
        if (variables[key] != null) {
            throw RuntimeError("Values must be immutable")
        } else {
            val valueSlot = ValueSlot(false, variable)
            variables[key] = valueSlot
        }
    }

    fun setMutable() {

    }

    fun importExternModule(name: String, module: FValue) {
        set(name, module)
    }
}