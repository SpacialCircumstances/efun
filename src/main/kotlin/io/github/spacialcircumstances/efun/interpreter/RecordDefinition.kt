package io.github.spacialcircumstances.efun.interpreter

class RecordDefinition(val name: String, val fieldDefs: Map<String, FType<*>>) {
    fun checkValues(values: Map<String, FType<*>>): Boolean {
        if (values.size != fieldDefs.size) return false
        values.forEach {
            val expectedType = fieldDefs[it.key]
            if (it.value != expectedType) return false
        }
        return true
    }

    fun createInstance(values: Map<String, FValue>): RecordInstance {
        return RecordInstance(this, values)
    }
}

class RecordInstance(val definition: RecordDefinition, val values: Map<String, FValue>): IFValueStore {
    override operator fun get(key: String): FValue? = values[key]
}