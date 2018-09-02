package io.github.spacialcircumstances.efun

import io.github.spacialcircumstances.efun.interpreter.*

class BindingExistsException(message: String, cause: Throwable? = null): Exception(message, cause)

class ExternalBinding {
    val bindings = mutableMapOf<String, FValue>()

    fun externalFloat(name: String, value: Double) {
        if (bindings.containsKey(name)) bindingExists(name)
        else {
            bindings[name] = FValue(TFloat, value)
        }
    }

    private fun externalInt(name: String, value: Long) {
        if (bindings.containsKey(name)) bindingExists(name)
        else {
            bindings[name] = FValue(TInt, value)
        }
    }

    fun externalString(name: String, value: String) {
        if (bindings.containsKey(name)) bindingExists(name)
        else {
            bindings[name] = FValue(TString, value)
        }
    }

    fun externalBoolean(name: String, value: Boolean) {
        if (bindings.containsKey(name)) bindingExists(name)
        else {
            bindings[name] = FValue(TBool, value)
        }
    }

    fun externalFunction(name: String, value: ExternalFunction) {
        if (bindings.containsKey(name)) bindingExists(name)
        else {
            bindings[name] = FValue(value.functionType, ExternalFunctionPointer(value))
        }
    }

    operator fun set(name: String, value: String) = ::externalString
    operator fun set(name: String, value: Double) = ::externalFloat
    operator fun set(name: String, value: Int) = ::externalInt
    operator fun set(name: String, value: Boolean) = ::externalBoolean

    operator fun get(name: String): FValue? = bindings[name]

    private fun bindingExists(name: String) {
        throw BindingExistsException("A binding to the external name $name already exists")
    }
}