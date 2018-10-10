package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.TypeError
import io.github.spacialcircumstances.efun.interpreter.*

class ConstructorExpression(private val typeName: PlaceholderType, val fields: List<Pair<String, AbstractExpression>>): AbstractExpression() {
    var type: DataStructureType? = null

    override fun evaluate(context: InterpreterContext): FValue {
        val params = fields.map { Pair(it.first, it.second.evaluate(context)) }.toMap()
        val dsContext = InterpreterContext(null)
        params.forEach { name, value ->
            dsContext[name] = value
        }
        val instance = DataStructureInstance(dsContext)
        return FValue(type!!, instance)
    }

    override fun guessType(context: TypesContext): FType<*> {
        type = typeName.resolveType(context) as DataStructureType
        if (type == null) throw TypeError("$typeName is not a data structure type")
        val params = fields.map { Pair(it.first, it.second.guessType(context)) }.toMap()
        val dataStructureContext = type!!.definition.typesContext
        params.forEach { name, t ->
            if (dataStructureContext.getType(name) != t) throw TypeError("Constructor type for $name does not match expected type $t")
        }
        return type!!
    }
}