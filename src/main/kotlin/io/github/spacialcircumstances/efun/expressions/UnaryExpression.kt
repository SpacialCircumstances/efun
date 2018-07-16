package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.FValueType
import io.github.spacialcircumstances.efun.interpreter.InterpreterContext
import io.github.spacialcircumstances.efun.parser.ret

class UnaryExpression(private val expression: AbstractExpression, private val operator: String): AbstractExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        val value = expression.evaluate(context)
        val result = when(operator) {
            "-" -> negate(value)
            "!" -> negate(value)
            else -> throw IllegalStateException("Unsupported operator: $operator")
        }
        return result
    }
}

fun negate(value: FValue): FValue {
    return when(value.type) {
        FValueType.Float -> FValue(FValueType.Float, -(value.value as Double))
        FValueType.Int -> FValue(FValueType.Int, -(value.value as Long))
        FValueType.Bool -> FValue(FValueType.Bool, !(value.value as Boolean))
        else -> throw IllegalStateException("Type ${value.type} not supported by operator")
    }
}