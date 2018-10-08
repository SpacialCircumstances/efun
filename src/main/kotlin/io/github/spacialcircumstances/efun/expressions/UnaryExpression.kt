package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.RuntimeError
import io.github.spacialcircumstances.efun.TypeError
import io.github.spacialcircumstances.efun.interpreter.*

class UnaryExpression(private val expression: AbstractExpression, private val operator: String): AbstractExpression() {
    override fun guessType(context: TypesContext): FType<*> {
        val subType = expression.guessType(context)
        if (!typeCheck(subType, operator)) {
            throw TypeError("Error typechecking: Cannot negate ${subType.name} with $operator")
        }
        return subType
    }

    override fun evaluate(context: InterpreterContext): FValue {
        val value = expression.evaluate(context)
        return when(operator) {
            "-" -> negate(value)
            "!" -> negate(value)
            else -> throw RuntimeError("Unsupported operator: $operator")
        }
    }
}

fun negate(value: FValue): FValue {
    return when(value.type) {
        TFloat -> FValue(TFloat, -TFloat.castValue(value))
        TInt -> FValue(TInt, -TInt.castValue(value))
        TBool -> FValue(TBool, !TBool.castValue(value))
        else -> throw RuntimeError("Type ${value.type} not supported by operator")
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