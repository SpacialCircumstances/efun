package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.RuntimeError
import io.github.spacialcircumstances.efun.Token
import io.github.spacialcircumstances.efun.TokenType
import io.github.spacialcircumstances.efun.TypeError
import io.github.spacialcircumstances.efun.expressions.binary.*
import io.github.spacialcircumstances.efun.interpreter.*

val operatorByToken = mapOf(
        TokenType.AND to BoolAndOperator(),
        TokenType.OR to BoolOrOperator(),
        TokenType.XOR to BoolXorOperator(),
        TokenType.EQUAL_EQUAL to AnyEqualOperator(),
        TokenType.BANG_EQUAL to AnyNotEqualOperator(),
        TokenType.PLUS to AdditionOperator(),
        TokenType.MINUS to SubstractionOperator(),
        TokenType.STAR to MultiplicationOperator(),
        TokenType.SLASH to DivisionOperator(),
        TokenType.LESS to SmallerOperator(),
        TokenType.LESS_EQUAL to SmallerEqualOperator(),
        TokenType.GREATER to GreaterOperator(),
        TokenType.GREATER_EQUAL to GreaterEqualOperator(),
        TokenType.IS to IsOperator()
)

class BinaryExpression(private val left: AbstractExpression, private val operator: Token, private val right: AbstractExpression) : AbstractExpression() {
    override fun guessType(context: TypeContext): FType<*> {
        val lt = left.guessType(context)
        val rt = right.guessType(context)
        val op = operatorByToken[operator.type] ?: throw TypeError("No operator found for token ${operator.lexeme}")
        return op.typecheck(lt, rt)
    }

    override fun evaluate(context: InterpreterContext): FValue {
        val l = left.evaluate(context)
        val r = right.evaluate(context)
        val op = operatorByToken[operator.type] ?: throw RuntimeError("No operator found for token ${operator.lexeme}")
        return op.compute(l, r)
    }
}