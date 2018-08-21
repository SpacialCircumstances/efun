package io.github.spacialcircumstances.efun.interpreter

import io.github.spacialcircumstances.efun.ExternalBinding
import io.github.spacialcircumstances.efun.Interpreter

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

fun defaultConfig(): InterpreterConfig {
    val extPow = ExternalFunction2<Double, Double, Double>({ d1, d2 ->
        Math.pow(d1, d2)
    }, createFunctionType(listOf(TFloat, TFloat), TFloat))
    val external = ExternalBinding()
    external.externalFloat("pi", Math.PI)
    external.externalFunction("pow", extPow)
    return InterpreterConfig(external)
}