package io.github.spacialcircumstances.efun.interpreter

class DataStructureType(override val name: String, val definition: DataStructureDefinition): FType<DataStructureInstance>() {
    override fun equals(other: Any?): Boolean {
        return if (other is DataStructureType) {
            other.definition == definition //TODO: Write proper equality
        } else false
    }

    override fun castValue(value: FValue): DataStructureInstance {
        return value.value as DataStructureInstance
    }

    override val subTypeStore: IFTypeStore? = definition
}