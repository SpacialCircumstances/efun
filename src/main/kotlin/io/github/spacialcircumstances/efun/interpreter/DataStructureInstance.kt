package io.github.spacialcircumstances.efun.interpreter

class DataStructureInstance(private val definition: DataStructureDefinition, private val interpreterContext: InterpreterContext): IFValueStore {
    override fun get(key: String): ValueSlot? = interpreterContext[key]

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("Datastructure of: {")

        interpreterContext.variables.forEach { name, value ->
            builder.append("$name (${value.type.name}): ")
            builder.append(value.value.toString())
            builder.append(",")
        }

        builder.append("}")
        return builder.toString()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is DataStructureInstance) {
            definition.instancesEqual(other, this)
        } else false
    }
}