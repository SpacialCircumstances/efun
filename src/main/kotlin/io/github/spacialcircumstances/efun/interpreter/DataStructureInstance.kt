package io.github.spacialcircumstances.efun.interpreter

class DataStructureInstance(private val interpreterContext: InterpreterContext): IFValueStore {
    override fun get(key: String): FValue? = interpreterContext[key]
}