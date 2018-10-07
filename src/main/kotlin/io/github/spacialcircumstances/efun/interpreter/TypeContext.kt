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

    fun importChildModule(moduleType: ModuleType) {
        val name = moduleType.name
        if (childModules.contains(name)) throw TypeError("Context already contains child module $name")
        childModules[name] = moduleType.module
        types[name] = moduleType
        typesResolveContext.importChildModule(name, moduleType.module)
    }

    fun importExternModule(moduleType: ModuleType) {
        typesResolveContext.importExternModule(moduleType.name, moduleType.module)
        types[moduleType.name] = moduleType
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