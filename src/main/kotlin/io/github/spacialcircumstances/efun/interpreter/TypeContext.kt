package io.github.spacialcircumstances.efun.interpreter

import io.github.spacialcircumstances.efun.TypeError

class TypeContext(val parent: TypeContext?) {
    private val types = mutableMapOf<String, FType<*>>()

    operator fun get(key: String): FType<*>? {
        return types[key] ?: parent?.get(key)
    }

    operator fun set(key: String, value: FType<*>) {
        if (types[key] != null) {
            throw TypeError("Cannot shadow variable $key")
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