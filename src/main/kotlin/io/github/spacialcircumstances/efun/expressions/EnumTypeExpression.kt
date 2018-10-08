package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

class EnumTypeExpression(elements: List<String>): AbstractExpression() {
    private val type = EnumType(elements.mapIndexed { index, s -> EnumInstance(s, index) })
    override fun evaluate(context: InterpreterContext): FValue {
        type.instances.map {
            context[it.name] = FValue(type, it)
        }
        return FValue(TVoid, null)
    }

    override fun guessType(context: TypesContext): EnumType {
        type.instances.map {
            context.registerPrivateType(it.name, type)
        }
        return type
    }
}