package io.github.spacialcircumstances.efun.interpreter

import io.github.spacialcircumstances.efun.TypeError

class TypeContext(private val parent: TypeContext?, additionalTypeMappings: Map<String, FType<*>>? = null): IFTypeStore {
    private val types = mutableMapOf<String, FType<*>>()
    private val typesResolveMap = mutableMapOf<String, FType<*>>()

    init {
        if (parent != null) {
            typesResolveMap.putAll(parent.typesResolveMap)
        }
        if (additionalTypeMappings != null) {
            typesResolveMap.putAll(additionalTypeMappings)
        }
    }

    override operator fun get(key: String): FType<*>? {
        return types[key] ?: parent?.get(key)
    }

    operator fun set(key: String, value: FType<*>) {
        if (types[key] != null) {
            throw TypeError("Cannot shadow variable $key")
        } else {
            types[key] = value
        }
    }

    fun registerType(name: String, type: FType<*>) {
        if (typesResolveMap.containsKey(name)) throw TypeError("Type $name already exists")
        typesResolveMap[name] = type
    }

    fun resolveSimpleType(placeholderType: SimplePlaceholderType): FType<*> {
        return typesResolveMap[placeholderType.name] ?: throw TypeError("Type ${placeholderType.name} not defined")
    }

    fun resolveFunctionType(placeholderType: FunctionPlaceholderType): FunctionType {
        val inTypes = placeholderType.args.map { it.resolveType(this) }
        val retType = placeholderType.returnType.resolveType(this)
        return createFunctionType(inTypes, retType)
    }

    fun copy(): TypeContext {
        val new = TypeContext(parent)
        new.types.putAll(types)
        new.typesResolveMap.putAll(typesResolveMap)
        return new
    }
}