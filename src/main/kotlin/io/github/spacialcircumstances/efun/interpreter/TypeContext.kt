package io.github.spacialcircumstances.efun.interpreter

class TypeContext(val parent: TypeContext?) {
    private val types = mutableMapOf<String, FType<*>>()

    operator fun get(key: String): FType<*>? {
        return types[key] ?: parent?.get(key)
    }

    operator fun set(key: String, value: FType<*>) {
        if (types[key] != null) {
            throw IllegalStateException("Cannot shadow variable $key")
        } else {
            types[key] = value
        }
    }

    fun copy(): TypeContext {
        val new = TypeContext(parent)
        new.types.putAll(types)
        return new
    }
}