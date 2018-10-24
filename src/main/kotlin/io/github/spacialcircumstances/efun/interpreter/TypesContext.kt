package io.github.spacialcircumstances.efun.interpreter

import io.github.spacialcircumstances.efun.TypeError

class TypesContext(private val parent: TypesContext?, val defaultTypeMappings: Map<String, FType<*>>): IFTypeStore {
    private val privateTypes = mutableMapOf<String, FType<*>>()
    private val publicTypes = mutableMapOf<String, FType<*>>()

    val types: Map<String, FType<*>>
        get() = publicTypes

    init {
        defaultTypeMappings.forEach { name, type ->
            privateTypes[name] = TypeType(type)
        }
    }

    override fun getType(key: String): FType<*>? = publicTypes[key] ?: privateTypes[key] ?: parent?.getType(key) ?: throw TypeError("Type for $key not found")

    fun getPublicType(key: String): FType<*>? = publicTypes[key] ?: parent?.getPublicType(key)

    fun registerPrivateType(name: String, type: FType<*>) {
        privateTypes[name] = type
    }

    fun registerPublicType(name: String, type: FType<*>) {
        publicTypes[name] = type
    }

    fun resolveType(simpleType: SimplePlaceholderType): FType<*> {
        val type = getType(simpleType.name) as TypeType
        return type.type
    }

    fun resolveType(placeholderType: FunctionPlaceholderType): FunctionType {
        val inTypes = placeholderType.args.map { it.resolveType(this) }
        val retType = placeholderType.returnType.resolveType(this)
        return createFunctionType(inTypes, retType)
    }

    fun importChildType(moduleType: DataStructureType) {
        registerPublicType(moduleType.name, TypeType(moduleType))
    }

    override fun equals(other: Any?): Boolean {
        return if (other is TypesContext) {
            other.publicTypes == this.publicTypes
        } else false
    }
}