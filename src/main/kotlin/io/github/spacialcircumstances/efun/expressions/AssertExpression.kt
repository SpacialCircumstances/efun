package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.InterpreterContext
import io.github.spacialcircumstances.efun.interpreter.TBool
import io.github.spacialcircumstances.efun.interpreter.TVoid

class AssertExpression(val value: AbstractExpression): AbstractExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        val result = value.evaluate(context)
        if (result.type != TBool) {
            throw IllegalStateException("Cannot assert on type ${result.type.name}")
        } else {
            val r = TBool.castValue(result)
            if (!r) {
                throw IllegalStateException("Assertion failed")
            }
        }
        return FValue(TVoid, null)
    }
}