package io.github.spacialcircumstances.efun.interpreter

class DataStructureDefinition(val typesContext: TypesContext): IFTypeStore {
    override fun getType(key: String): FType<*>? = typesContext.getPublicType(key)
    override fun equals(other: Any?): Boolean {
        return if (other is DataStructureDefinition) {
            other.typesContext == typesContext
        } else false
    }

    fun instancesEqual(dataStructureInstance1: DataStructureInstance, dataStructureInstance2: DataStructureInstance): Boolean {
        val publicTypes = typesContext.types

        return publicTypes.all {
            val v1 = dataStructureInstance1[it.key]
            val v2 = dataStructureInstance2[it.key]
            v1 != null && v1 == v2
        }
    }

    fun isSubDefinition(other: DataStructureDefinition): Boolean = other.typesContext.types.all {
            this.typesContext.types[it.key] == it.value
        }
}