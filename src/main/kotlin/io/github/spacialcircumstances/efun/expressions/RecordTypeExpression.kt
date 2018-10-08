package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

class RecordTypeExpression(private val name: String, private val elements: List<Pair<String, PlaceholderType>>): AbstractExpression() {
    override fun evaluate(context: InterpreterContext): FValue = FValue(TVoid, null)

    override fun guessType(context: TypesContext): FType<*> {
        val recordDefinition = RecordDefinition(name, elements.map { Pair(it.first, it.second.resolveType(context)) }.toMap())
        return RecordType(recordDefinition)
    }
}