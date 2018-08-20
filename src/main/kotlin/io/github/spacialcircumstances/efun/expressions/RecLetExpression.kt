package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

class RecLetExpression(val name: String, val retType: PlaceholderType, val expr: BlockExpression): AbstractExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun guessType(context: TypeContext): FType<*> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}