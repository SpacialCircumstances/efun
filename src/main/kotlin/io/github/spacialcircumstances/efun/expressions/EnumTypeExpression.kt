package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.FType
import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.InterpreterContext
import io.github.spacialcircumstances.efun.interpreter.TypeContext

class EnumTypeExpression(val elements: List<String>): AbstractExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun guessType(context: TypeContext): FType<*> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}