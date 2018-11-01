package io.github.spacialcircumstances.efun.interpreter

class DataStructureType(override val name: String, val definition: DataStructureDefinition, val isSignature: Boolean = false): FType<DataStructureInstance>() {
    override fun isSubType(type: FType<*>): Boolean = if (type is DataStructureType) definition.isSubDefinition(type.definition) else false

    override fun equals(other: Any?): Boolean {
        return if (other is DataStructureType) {
            other.definition == definition
        } else false
    }

    override fun castValue(value: FValue): DataStructureInstance {
        return value.value as DataStructureInstance
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + definition.hashCode()
        return result
    }

    override val subTypeStore: IFTypeStore? = definition
}