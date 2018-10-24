package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.DataStructureType
import io.github.spacialcircumstances.efun.interpreter.InterpreterContext
import io.github.spacialcircumstances.efun.interpreter.PlaceholderType
import io.github.spacialcircumstances.efun.interpreter.TypesContext

class SignatureExpression(private val parents: List<PlaceholderType>, private val members: Map<String, PlaceholderType>): AbstractTypeExpression() {
    override fun type(context: TypesContext): DataStructureType {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun evaluate(context: InterpreterContext) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}