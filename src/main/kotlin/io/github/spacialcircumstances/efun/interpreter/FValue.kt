package io.github.spacialcircumstances.efun.interpreter

class FValue(val type: FType<*>, val value: Any?) {
    override fun toString(): String {
        return "$value ($type)"
    }
}