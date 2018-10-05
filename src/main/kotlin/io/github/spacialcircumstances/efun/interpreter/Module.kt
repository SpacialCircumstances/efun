package io.github.spacialcircumstances.efun.interpreter

class Module(val typeContext: TypeContext): IFTypeStore {
    override fun getType(key: String): FType<*>? = typeContext.getType(key)
}