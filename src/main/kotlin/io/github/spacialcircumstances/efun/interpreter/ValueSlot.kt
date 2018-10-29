package io.github.spacialcircumstances.efun.interpreter

import io.github.spacialcircumstances.efun.RuntimeError

class ValueSlot(val mutable: Boolean, private var valueInstance: FValue) {
    val type
    get() = valueInstance.type

    var value
    get() = valueInstance
    set(value) = if (mutable) valueInstance = value else throw RuntimeError("Cannot modify this variable")

}