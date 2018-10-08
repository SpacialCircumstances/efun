package io.github.spacialcircumstances.efun.interpreter

class ModuleInstance(val interpreterContext: InterpreterContext): IFValueStore {
    override fun get(key: String): FValue? = interpreterContext[key]
}