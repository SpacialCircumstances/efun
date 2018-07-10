package io.github.spacialcircumstances.efun

import io.github.spacialcircumstances.efun.expressions.LiteralExpression
import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.FValueType

fun parseLiteral(tokens: List<Token>): Pair<List<LiteralExpression>?, List<Token>> {
    val first = tokens.first()
    val rest = if (tokens.size > 1) tokens.subList(1, tokens.size) else emptyList()
    return when (first.type) {
        TokenType.STRING -> Pair(listOf(LiteralExpression(FValue(FValueType.String, first.literal))), rest)
        TokenType.INTEGER -> Pair(listOf(LiteralExpression(FValue(FValueType.Int, first.literal))), rest)
        TokenType.FLOAT -> Pair(listOf(LiteralExpression(FValue(FValueType.Float, first.literal))), rest)
        else -> Pair(null, tokens)
    }
}