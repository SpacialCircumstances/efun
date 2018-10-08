package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.TypeError
import io.github.spacialcircumstances.efun.interpreter.*

class ConstructorExpression(private val typeName: PlaceholderType, val fields: List<Pair<String, AbstractExpression>>): AbstractExpression() {
    var type: RecordType? = null

    override fun evaluate(context: InterpreterContext): FValue {
        val params = fields.map { Pair(it.first, it.second.evaluate(context)) }.toMap()
        val instance = type!!.definition.createInstance(params)
        return FValue(type!!, instance)
    }

    override fun guessType(context: TypesContext): FType<*> {
        type = typeName.resolveType(context) as RecordType
        if (type == null) throw TypeError("$typeName is not a record type")
        val params = fields.map { Pair(it.first, it.second.guessType(context)) }.toMap()
        if (!type!!.definition.checkValues(params)) {
            throw TypeError("Error in constructor for type ${type!!.name}")
        }
        return type!!
    }
}