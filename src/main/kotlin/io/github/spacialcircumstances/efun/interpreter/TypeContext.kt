package io.github.spacialcircumstances.efun.interpreter

import io.github.spacialcircumstances.efun.TypeError

class TypeContext(private val parent: TypeContext?, val additionalTypeMappings: Map<String, FType<*>>? = null): IFTypeStore {
    private val types = mutableMapOf<String, FType<*>>()
    val childModules = mutableMapOf<String, Module>()
    val typesResolveContext: TypeResolveContext = TypeResolveContext(parent?.typesResolveContext, additionalTypeMappings)

    override fun getType(key: String): FType<*>? {
        return types[key] ?: parent?.getType(key)
    }

    operator fun set(key: String, value: FType<*>) {
        if (types[key] != null) {
            throw TypeError("Cannot shadow variable $key")
        } else {
            types[key] = value
        }
    }

    fun registerChildModule(name: String, module: Module) {
        if (childModules.contains(name)) throw TypeError("Context already contains child module $name")
        childModules[name] = module
    }

    fun registerType(name: String, type: FType<*>) {
        typesResolveContext.registerType(name, type)
    }

    fun resolveSimpleType(placeholderType: SimplePlaceholderType): FType<*> =
        typesResolveContext.resolveType(placeholderType.name)

    fun resolveFunctionType(placeholderType: FunctionPlaceholderType): FunctionType {
        val inTypes = placeholderType.args.map { it.resolveType(this) }
        val retType = placeholderType.returnType.resolveType(this)
        return createFunctionType(inTypes, retType)
    }
}