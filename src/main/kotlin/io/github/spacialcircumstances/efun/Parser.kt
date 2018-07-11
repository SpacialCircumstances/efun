package io.github.spacialcircumstances.efun

import io.github.spacialcircumstances.efun.expressions.AbstractExpression
import io.github.spacialcircumstances.efun.expressions.LiteralExpression
import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.FValueType
import io.github.spacialcircumstances.efun.parser.choice
import io.github.spacialcircumstances.efun.parser.one
import io.github.spacialcircumstances.efun.parser.orElse

fun parseLiteral(tokens: List<Token>): Pair<List<LiteralExpression>?, List<Token>> {
    if (tokens.isEmpty()) return Pair(null, tokens)
    val first = tokens.first()
    val rest = if (tokens.size > 1) tokens.subList(1, tokens.size) else emptyList()
    return when (first.type) {
        TokenType.STRING -> Pair(listOf(LiteralExpression(FValue(FValueType.String, first.literal))), rest)
        TokenType.INTEGER -> Pair(listOf(LiteralExpression(FValue(FValueType.Int, first.literal))), rest)
        TokenType.FLOAT -> Pair(listOf(LiteralExpression(FValue(FValueType.Float, first.literal))), rest)
        else -> Pair(null, tokens)
    }
}

val stringLiteralParser = one<Token, AbstractExpression>({ it.type == TokenType.STRING }) {
    LiteralExpression(FValue(FValueType.String, it.literal))
}

val intLiteralParser = one<Token, AbstractExpression>({ it.type == TokenType.INTEGER }) {
    LiteralExpression(FValue(FValueType.Int, it.literal))
}

val floatLiteralParser = one<Token, AbstractExpression>({ it.type == TokenType.FLOAT }) {
    LiteralExpression(FValue(FValueType.Float, it.literal))
}

val literalParser = choice(stringLiteralParser, intLiteralParser, floatLiteralParser)