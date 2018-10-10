package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

class RecordTypeExpression(private val name: String, private val elements: List<Pair<String, PlaceholderType>>): AbstractTypeExpression() {
    override fun evaluate(context: InterpreterContext): FValue = TODO()

    override fun type(context: TypesContext): DataStructureType {
        TODO()
    }
}