package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

class UnaryExpression(private val expression: AbstractExpression, private val operator: String): AbstractExpression() {
    override fun guessType(context: TypeContext): FType<*> {
        val subType = expression.guessType(context)
        if (!typeCheck(subType, operator)) {
            throw IllegalStateException("Error typechecking: Cannot negate ${subType.name} with $operator")
        }
        return subType
    }

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
        TFloat -> FValue(TFloat, -TFloat.castValue(value))
        TInt -> FValue(TInt, -TInt.castValue(value))
        TBool -> FValue(TBool, !TBool.castValue(value))
        else -> throw IllegalStateException("Type ${value.type} not supported by operator")
    }
}

fun typeCheck(type: FType<*>, operator: String): Boolean {
    return when (operator) {
        "-" -> {
            (type == TInt) || (type == TFloat)
        }
        "!" -> type == TBool
        else -> false
    }
}