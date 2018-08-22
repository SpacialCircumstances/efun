package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

class RecordTypeExpression(private val elements: List<Pair<String, PlaceholderType>>): AbstractExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun guessType(context: TypeContext): FType<*> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}