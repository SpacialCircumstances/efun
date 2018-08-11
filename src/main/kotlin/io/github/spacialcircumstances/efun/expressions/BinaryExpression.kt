package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.Token
import io.github.spacialcircumstances.efun.TokenType
import io.github.spacialcircumstances.efun.interpreter.*

class BinaryExpression(private val left: AbstractExpression, private val operator: Token, private val right: AbstractExpression) : AbstractExpression() {
    override fun guessType(context: TypeContext): FType<*> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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
        TokenType.LESS_EQUAL -> lesserEqual(l, r)
        TokenType.LESS -> lesser(l, r)
        TokenType.GREATER -> greater(l, r)
        TokenType.GREATER_EQUAL -> greaterEqual(l, r)
        TokenType.MINUS -> minus(l, r)
        TokenType.PLUS -> plus(l, r)
        TokenType.STAR -> mul(l, r)
        TokenType.SLASH -> div(l, r)
        TokenType.AND -> and(l, r)
        TokenType.OR -> or(l, r)
        TokenType.XOR -> xor(l, r)
        else -> throw IllegalStateException("Invalid operator: ${operator.lexeme}")
    }
}

fun and(l: FValue, r: FValue): FValue {
    if (l.type != TBool || r.type != TBool) throw IllegalStateException("Operator and does not work on types ${l.type} and ${r.type}")
    return FValue(TBool, TBool.castValue(l) && TBool.castValue(r))
}

fun or(l: FValue, r: FValue): FValue {
    if (l.type != TBool || r.type != TBool) throw IllegalStateException("Operator or does not work on types ${l.type} and ${r.type}")
    return FValue(TBool, TBool.castValue(l) || TBool.castValue(r))
}

fun xor(l: FValue, r: FValue): FValue {
    if (l.type != TBool || r.type != TBool) throw IllegalStateException("Operator xor does not work on types ${l.type} and ${r.type}")
    return FValue(TBool, TBool.castValue(l) xor TBool.castValue(r))
}

fun greater(l: FValue, r: FValue): FValue {
    if (! checkNumeric(l.type) || ! checkNumeric(r.type)) {
        throw IllegalStateException("Operator > does not work on types ${l.type} and ${r.type}")
    } else {
        return when {
            l.type == TFloat -> FValue(TBool, TFloat.castValue(l) > TFloat.castValue(r))
            l.type == TInt -> FValue(TBool, TInt.castValue(l) > TInt.castValue(r))
            else -> throw IllegalStateException()
        }
    }
}

fun lesser(l: FValue, r: FValue): FValue {
    if (! checkNumeric(l.type) || ! checkNumeric(r.type)) {
        throw IllegalStateException("Operator > does not work on types ${l.type} and ${r.type}")
    } else {
        return when {
            l.type == TFloat -> FValue(TBool, TFloat.castValue(l) < TFloat.castValue(r))
            l.type == TInt -> FValue(TBool, TInt.castValue(l) < TInt.castValue(r))
            else -> throw IllegalStateException()
        }
    }
}

fun greaterEqual(l: FValue, r: FValue): FValue {
    if (! checkNumeric(l.type) || ! checkNumeric(r.type)) {
        throw IllegalStateException("Operator > does not work on types ${l.type} and ${r.type}")
    } else {
        return when {
            l.type == TFloat -> FValue(TBool, TFloat.castValue(l) >= TFloat.castValue(r))
            l.type == TInt -> FValue(TBool, TInt.castValue(l) >= TInt.castValue(r))
            else -> throw IllegalStateException()
        }
    }
}

fun lesserEqual(l: FValue, r: FValue): FValue {
    if (! checkNumeric(l.type) || ! checkNumeric(r.type)) {
        throw IllegalStateException("Operator > does not work on types ${l.type} and ${r.type}")
    } else {
        return when {
            l.type == TFloat -> FValue(TBool, TFloat.castValue(l) <= TFloat.castValue(r))
            l.type == TInt -> FValue(TBool, TInt.castValue(l) <= TInt.castValue(r))
            else -> throw IllegalStateException()
        }
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