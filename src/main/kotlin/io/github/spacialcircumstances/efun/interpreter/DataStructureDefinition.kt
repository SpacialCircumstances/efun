package io.github.spacialcircumstances.efun.interpreter

class DataStructureDefinition(val typesContext: TypesContext): IFTypeStore {
    override fun getType(key: String): FType<*>? = typesContext.getType(key)
}