package io.github.spacialcircumstances.efun.interpreter

class InterpreterConfig {
    fun createInterpreterContext(): InterpreterContext {
        return InterpreterContext(null)
    }

    fun createTypeContext(): TypeContext {
        return TypeContext(null)
    }
}