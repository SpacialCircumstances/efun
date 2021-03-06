package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.RuntimeError
import io.github.spacialcircumstances.efun.interpreter.*

class AssertExpression(val value: AbstractExpression): AbstractExpression() {
    override fun guessType(context: TypesContext): FType<*> {
        value.guessType(context)
        return TVoid
    }

    override fun evaluate(context: InterpreterContext): FValue {
        val result = value.evaluate(context)
        if (result.type != TBool) {
            throw RuntimeError("Cannot assert on type ${result.type.name}")
        } else {
            val r = TBool.castValue(result)
            if (!r) {
                throw RuntimeError("Assertion failed")
            }
        }
        return FValue(TVoid, null)
    }
}