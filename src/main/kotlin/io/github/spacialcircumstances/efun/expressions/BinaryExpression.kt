package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.Token
import io.github.spacialcircumstances.efun.TokenType
import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.FValueType
import io.github.spacialcircumstances.efun.interpreter.InterpreterContext
import io.github.spacialcircumstances.efun.interpreter.checkNumeric

class BinaryExpression(private val left: AbstractExpression, private val operator: Token, private val right: AbstractExpression) : AbstractExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        val l = left.evaluate(context)
        val r = right.evaluate(context)
        return computeBinary(operator, l, r)
    }
}

fun computeBinary(operator: Token, l: FValue, r: FValue) : FValue {
    return when (operator.type) {
        TokenType.MINUS -> minus(l, r)
        TokenType.PLUS -> plus(l, r)
        TokenType.STAR -> mul(l, r)
        TokenType.SLASH -> div(l, r)
        else -> throw IllegalStateException("Invalid operator: ${operator.lexeme}")
    }
}

fun div(l: FValue, r: FValue): FValue {
    if (! checkNumeric(l.type) || ! checkNumeric(r.type)) {
        throw IllegalStateException("Division does not work on type: ${l.type}")
    } else {
        return when {
            l.type == FValueType.Float -> FValue(FValueType.Float, l.value as Double / r.value as Double)
            l.type == FValueType.Int -> FValue(FValueType.Int, l.value as Long / r.value as Long)
            else -> throw IllegalStateException()
        }
    }
}

fun mul(l: FValue, r: FValue): FValue {
    if (! checkNumeric(l.type) || ! checkNumeric(r.type)) {
        throw IllegalStateException("Multiplication does not work on type: ${l.type}")
    } else {
        return when {
            l.type == FValueType.Float -> FValue(FValueType.Float, l.value as Double * r.value as Double)
            l.type == FValueType.Int -> FValue(FValueType.Int, l.value as Long * r.value as Long)
            else -> throw IllegalStateException()
        }
    }
}

fun plus(l: FValue, r: FValue): FValue {
    if (! checkNumeric(l.type) || ! checkNumeric(r.type)) {
        throw IllegalStateException("Addition does not work on type: ${l.type}")
    } else {
        return when {
            l.type == FValueType.Float -> FValue(FValueType.Float, l.value as Double + r.value as Double)
            l.type == FValueType.Int -> FValue(FValueType.Int, l.value as Long + r.value as Long)
            else -> throw IllegalStateException()
        }
    }
}

fun minus(l: FValue, r: FValue): FValue {
    if (! checkNumeric(l.type) || ! checkNumeric(r.type)) {
        throw IllegalStateException("Subtraction does not work on type: ${l.type}")
    } else {
        return when {
            l.type == FValueType.Float -> FValue(FValueType.Float, l.value as Double - r.value as Double)
            l.type == FValueType.Int -> FValue(FValueType.Int, l.value as Long - r.value as Long)
            else -> throw IllegalStateException()
        }
    }
}