package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

class ObjectTypeExpression(val params: Map<String, PlaceholderType>, val body: List<AbstractExpression>): AbstractTypeExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun type(context: TypesContext): DataStructureType {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}