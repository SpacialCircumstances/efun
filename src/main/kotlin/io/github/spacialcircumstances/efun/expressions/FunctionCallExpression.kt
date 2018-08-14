package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.TypeError
import io.github.spacialcircumstances.efun.interpreter.*

class FunctionCallExpression(private val functionExpression: AbstractExpression, private val args: List<AbstractExpression>): AbstractExpression() {
    override fun guessType(context: TypeContext): FType<*> {
        val fType = functionExpression.guessType(context) as FunctionType
        val argTypes = args.map { it.guessType(context) }
        var result: FType<*> = fType
        for (i in 0 until argTypes.size) {
            val argType = argTypes[i]
            (result as? FunctionType)?.let {
                if (it.inType == argType) {
                    result = it.outType
                } else {
                    throw TypeError("Incompatible types for function: got ${argType.name}, expected ${it.inType.name}")
                }
            } ?: break
        }
        return result
    }

    override fun evaluate(context: InterpreterContext): FValue {
        val function = functionExpression.evaluate(context).value as FunctionPointer
        return runWhileFunction(function, args.map { it.evaluate(context) })
    }
}