package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.InterpreterContext

class FunctionCallExpression(private val functionExpression: AbstractExpression, private val args: List<AbstractExpression>): AbstractExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}