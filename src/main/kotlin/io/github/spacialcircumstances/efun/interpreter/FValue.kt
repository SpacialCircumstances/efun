package io.github.spacialcircumstances.efun.interpreter

class FValue(val type: FValueType, val value: Any?) {
    override fun toString(): String {
        return "$value ($type)"
    }
}