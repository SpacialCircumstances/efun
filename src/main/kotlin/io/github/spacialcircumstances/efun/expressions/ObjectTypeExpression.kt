package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

class ObjectTypeExpression(val name: String, val params: Map<String, PlaceholderType>, val body: List<AbstractExpression>): AbstractTypeExpression() {
    var type: DataStructureType? = null
    var constructorType: FunctionType? = null
    val constructorName = "new$name"
    override fun evaluate(context: InterpreterContext) {
        //TODO: Create constructor

    }

    override fun type(context: TypesContext): DataStructureType {
        val objectContext = TypesContext(null, context.defaultTypeMappings)
        val resolvedParams = params.map { Pair(it.key, it.value.resolveType(context)) }
        resolvedParams.forEach { (name, t) ->
            objectContext.registerPrivateType(name, t)
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