package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

class RecordTypeExpression(private val name: String, private val elements: List<Pair<String, PlaceholderType>>): AbstractTypeExpression() {
    var type: DataStructureType? = null

    override fun evaluate(context: InterpreterContext): FValue = FValue(TVoid, null)

    override fun type(context: TypesContext): DataStructureType {
        val recordContext = TypesContext(null, context.defaultTypeMappings)
        elements.forEach {
            recordContext.registerPublicType(it.first, it.second.resolveType(context))
        }
        val definition = DataStructureDefinition(recordContext)
        type = DataStructureType(name, definition)
        return type!!
    }
}