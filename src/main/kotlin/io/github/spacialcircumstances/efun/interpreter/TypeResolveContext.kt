package io.github.spacialcircumstances.efun.interpreter

import io.github.spacialcircumstances.efun.TypeError

class TypeResolveContext(parent: TypeResolveContext?, additionalTypeMappings: Map<String, FType<*>>?) {
    private val externTypes = mutableMapOf<String, FType<*>>()
    private val moduleTypes = mutableMapOf<String, FType<*>>()
    private val childModuleTypes = mutableMapOf<String, FType<*>>()
    private val scopeTypes = mutableMapOf<String, FType<*>>()

    init {
        if (additionalTypeMappings != null) {
            externTypes.putAll(additionalTypeMappings)
        }

        if (parent != null) {
            scopeTypes.putAll(parent.scopeTypes)
            externTypes.putAll(parent.externTypes)
        }
    }

    fun importChildModule(name: String, module: Module) {
        module.typeContext.typesResolveContext.scopeTypes.forEach { typeName, type ->
            val newName = "$name.$typeName"
            if (childModuleTypes.containsKey(newName)) throw TypeError("Type $newName already exists")
            childModuleTypes[newName] = type
        }
    }

    fun importExternModule(name: String, module: Module) {
        module.typeContext.typesResolveContext.scopeTypes.forEach { typeName, type ->
            val newName = "$name.$typeName"
            if (moduleTypes.containsKey(newName)) throw TypeError("Type $newName already exists")
            moduleTypes[newName] = type
        }
    }

    fun resolveType(name: String): FType<*> =
        scopeTypes[name] ?: externTypes[name] ?: childModuleTypes[name] ?: moduleTypes[name] ?: throw TypeError("Type $name not found")

    fun registerType(name: String, type: FType<*>) {
        if (scopeTypes.containsKey(name)) throw TypeError("Type $name already exists")
        scopeTypes[name] = type
    }
}