package io.github.spacialcircumstances.efun.interpreter

import io.github.spacialcircumstances.efun.ExternalBinding

class InterpreterConfig(private val externalBinding: ExternalBinding) {
    private val externalValues = externalBinding.bindings
    private val defaultTypeMappings = mapOf<String, FType<*>>(
            "Int" to TInt,
            "String" to TString,
            "Void" to TVoid,
            "Float" to TFloat,
            "Bool" to TBool
    )

    fun createInterpreterContext(): InterpreterContext {
        val context = InterpreterContext(null)
        externalValues.forEach {
            context[it.key] = it.value
        }
        return context
    }

    fun createTypeContext(): TypeContext {
        val context = TypeContext(null, defaultTypeMappings)
        externalValues.forEach {
            context[it.key] = it.value.type
        }
        return context
    }
}