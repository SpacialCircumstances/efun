package io.github.spacialcircumstances.efun.interpreter

class Module(val typeContext: TypesContext): IFTypeStore {
    override fun getType(key: String): FType<*>? = typeContext.getType(key)
}