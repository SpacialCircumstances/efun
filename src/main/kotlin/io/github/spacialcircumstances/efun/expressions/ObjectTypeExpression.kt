package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

data class ObjectParameter(val name: String, val type: PlaceholderType, val isPublic: Boolean)

class ObjectTypeExpression(val name: String, val params: List<ObjectParameter>, val body: List<AbstractExpression>): AbstractTypeExpression() {
    var type: DataStructureType? = null
    var constructorType: FunctionType? = null
    val constructorName = "new$name"
    override fun evaluate(context: InterpreterContext) {
        val constr = createConstructor(params.map { it.name }, constructorType!!, type!!, context, body)
        context[constructorName] = FValue(constructorType!!, constr)
    }

    override fun type(context: TypesContext): DataStructureType {
        val objectContext = TypesContext(null, context.defaultTypeMappings)
        val resolvedParams = params.map {
            val paramType = it.type.resolveType(context)
            if (it.isPublic) {
                objectContext.registerPublicType(it.name, paramType)
            } else {
                objectContext.registerPrivateType(it.name, paramType)
            }
            Pair(it.name, it.type.resolveType(context))
        }
        body.forEach {
            it.guessType(objectContext)
        }
        val dataStructureDefinition = DataStructureDefinition(objectContext)
        type = DataStructureType(name, dataStructureDefinition)
        constructorType = createFunctionType(resolvedParams.map { it.second }, type!!)
        context.registerPublicType(constructorName, constructorType!!)
        return type!!
    }
}