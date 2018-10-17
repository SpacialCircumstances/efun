package io.github.spacialcircumstances.efun.interpreter

class DataStructureInstance(private val interpreterContext: InterpreterContext): IFValueStore {
    override fun get(key: String): FValue? = interpreterContext[key]

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
}