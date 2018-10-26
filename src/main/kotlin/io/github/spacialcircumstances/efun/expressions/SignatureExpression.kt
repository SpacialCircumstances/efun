package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.TypeError
import io.github.spacialcircumstances.efun.interpreter.*

class SignatureExpression(private val name: String, private val parents: List<PlaceholderType>, private val members: Map<String, PlaceholderType>): AbstractTypeExpression() {
    override fun type(context: TypesContext): DataStructureType {
        val signatureContext = TypesContext(null, context.defaultTypeMappings)

        parents.forEach {
            val type = it.resolveType(context) as? DataStructureType
            if (type == null || !type.isSignature) {
                throw TypeError("Signatures can only have signatures as parents")
            } else {
                type.definition.typesContext.types.forEach { name, defType ->
                    signatureContext.registerPublicType(name, defType)
                }
            }
        }

        members.forEach { name, type ->
            val rType = type.resolveType(context)
            signatureContext.registerPublicType(name, rType)
        }

        val definition = DataStructureDefinition(signatureContext)
        val type = DataStructureType(name, definition, true)
        context.registerPublicType(name, type)
        return type
    }

    override fun evaluate(context: InterpreterContext) {
    }
}