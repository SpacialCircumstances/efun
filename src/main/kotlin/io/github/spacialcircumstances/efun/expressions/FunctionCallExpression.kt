package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

class FunctionCallExpression(private val functionExpression: AbstractExpression, private val args: List<AbstractExpression>): AbstractExpression() {
    override fun guessType(context: TypeContext): FType<*> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun evaluate(context: InterpreterContext): FValue {
        val function = functionExpression.evaluate(context).value as FunctionPointer
        return runWhileFunction(function, args.map { it.evaluate(context) })
    }
}