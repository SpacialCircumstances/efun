package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.Token
import io.github.spacialcircumstances.efun.TokenType
import io.github.spacialcircumstances.efun.interpreter.*

class BinaryExpression(private val left: AbstractExpression, private val operator: Token, private val right: AbstractExpression) : AbstractExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        val l = left.evaluate(context)
        val r = right.evaluate(context)
        return computeBinary(operator, l, r)
    }
}

fun computeBinary(operator: Token, l: FValue, r: FValue) : FValue {
    return when (operator.type) {
        TokenType.EQUAL_EQUAL -> equal(l, r)
        TokenType.BANG_EQUAL -> notEqual(l, r)
        TokenType.MINUS -> minus(l, r)
        TokenType.PLUS -> plus(l, r)
        TokenType.STAR -> mul(l, r)
        TokenType.SLASH -> div(l, r)
        else -> throw IllegalStateException("Invalid operator: ${operator.lexeme}")
    }
}

fun equal(l: FValue, r: FValue): FValue {
    if (l.type != r.type) {
        throw IllegalStateException("Cannot use operator == on types: ${l.type} and ${r.type}")
    } else {
        return FValue(TBool, l.type.castValue(l) == r.type.castValue(r))
    }
}

fun notEqual(l: FValue, r: FValue): FValue {
    if (l.type != r.type) {
        throw IllegalStateException("Cannot use operator != on types: ${l.type} and ${r.type}")
    } else {
        return FValue(TBool, l.type.castValue(l) != r.type.castValue(r))
    }
}

fun div(l: FValue, r: FValue): FValue {
    if (! checkNumeric(l.type) || ! checkNumeric(r.type)) {
        throw IllegalStateException("Division does not work on type: ${l.type}")
    } else {
        return when {
            l.type == TFloat -> FValue(TFloat, TFloat.castValue(l) / TFloat.castValue(r))
            l.type == TInt -> FValue(TInt, TInt.castValue(l) / TInt.castValue(r))
            else -> throw IllegalStateException()
        }
    }
}

fun mul(l: FValue, r: FValue): FValue {
    if (! checkNumeric(l.type) || ! checkNumeric(r.type)) {
        throw IllegalStateException("Multiplication does not work on type: ${l.type}")
    } else {
        return when {
            l.type == TFloat -> FValue(TFloat, TFloat.castValue(l) * TFloat.castValue(r))
            l.type == TInt -> FValue(TInt, TInt.castValue(l) * TInt.castValue(r))
            else -> throw IllegalStateException()
        }
    }
}

fun plus(l: FValue, r: FValue): FValue {
    if (! checkNumeric(l.type) || ! checkNumeric(r.type)) {
        throw IllegalStateException("Addition does not work on type: ${l.type}")
    } else {
        return when {
            l.type == TFloat -> FValue(TFloat, TFloat.castValue(l) + TFloat.castValue(r))
            l.type == TInt -> FValue(TInt, TInt.castValue(l) + TInt.castValue(r))
            else -> throw IllegalStateException()
        }
    }
}

fun minus(l: FValue, r: FValue): FValue {
    if (! checkNumeric(l.type) || ! checkNumeric(r.type)) {
        throw IllegalStateException("Subtraction does not work on type: ${l.type}")
    } else {
        return when {
            l.type == TFloat -> FValue(TFloat, TFloat.castValue(l) - TFloat.castValue(r))
            l.type == TInt -> FValue(TInt, TInt.castValue(l) - TInt.castValue(r))
            else -> throw IllegalStateException()
        }
    }
}