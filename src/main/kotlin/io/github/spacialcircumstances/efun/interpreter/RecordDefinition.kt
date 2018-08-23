package io.github.spacialcircumstances.efun.interpreter

class RecordDefinition(val name: String, val fieldDefs: List<Pair<String, FType<*>>>) {
    fun checkValues(values: List<FValue>): Boolean {
        if (values.size != fieldDefs.size) return false
        values.forEachIndexed { index, fValue ->
            if (fValue.type != fieldDefs[index].second) {
                return false
            }
        }
        return true
    }
}

class RecordInstance(val definition: RecordDefinition, values: List<FValue>) {
    val fields = mapOf<String, FValue>()
}