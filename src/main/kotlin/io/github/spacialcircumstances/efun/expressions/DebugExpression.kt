package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.InterpreterContext
import io.github.spacialcircumstances.efun.interpreter.TVoid

class DebugExpression(private val expression: AbstractExpression): AbstractExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        println(expression.evaluate(context).value)
        return FValue(TVoid, null)
    }
}