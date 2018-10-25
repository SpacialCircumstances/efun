package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*
import kotlin.math.sign

class SignatureExpression(private val name: String, private val parents: List<PlaceholderType>, private val members: Map<String, PlaceholderType>): AbstractTypeExpression() {
    override fun type(context: TypesContext): DataStructureType {
        val signatureContext = TypesContext(null, context.defaultTypeMappings)

        members.forEach { name, type ->
            val rType = type.resolveType(context)
            signatureContext.registerPublicType(name, rType)
        }

        val definition = DataStructureDefinition(signatureContext)
        val type = DataStructureType(name, definition)
        context.registerPublicType(name, type)
        return type
    }

    override fun evaluate(context: InterpreterContext) {
    }
}