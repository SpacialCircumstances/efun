package io.github.spacialcircumstances.efun

import io.github.spacialcircumstances.efun.expressions.AbstractExpression
import io.github.spacialcircumstances.efun.expressions.DebugExpression
import io.github.spacialcircumstances.efun.expressions.LetExpression
import io.github.spacialcircumstances.efun.expressions.LiteralExpression
import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.FValueType
import io.github.spacialcircumstances.efun.parser.*

val stringLiteralParser = oneWith<LiteralExpression, Token>({ it.type == TokenType.STRING }) {
    LiteralExpression(FValue(FValueType.String, it.literal))
}

val intLiteralParser = oneWith<LiteralExpression, Token>({ it.type == TokenType.INTEGER }) {
    LiteralExpression(FValue(FValueType.Int, it.literal))
}

val floatLiteralParser = oneWith<LiteralExpression, Token>({ it.type == TokenType.FLOAT }) {
    LiteralExpression(FValue(FValueType.Float, it.literal))
}

val trueLiteralParser = oneWith<LiteralExpression, Token>({ it.type == TokenType.TRUE }) {
    LiteralExpression(FValue(FValueType.Bool, true))
}

val falseLiteralParser = oneWith<LiteralExpression, Token>({ it.type == TokenType.FALSE }) {
    LiteralExpression(FValue(FValueType.Bool, false))
}

val expressionParser = lazy(::createExpressionParser)

val literalParser = choice(stringLiteralParser, intLiteralParser, floatLiteralParser, trueLiteralParser, falseLiteralParser)

val arrowParser = one<Token> { it.type == TokenType.ARROW }

val nameParser = oneWith<String, Token>({ it.type == TokenType.IDENTIFIER }) {
    it.literal as String
}

val letNameParser = takeMiddle(one { it.type == TokenType.LET },
                                nameParser,
                                one { it.type == TokenType.EQUAL })

val letExpressionParser = letNameParser.andThen(expressionParser).map {
    LetExpression(it.second, it.first)
}

val openParensParser = one<Token> { it.type == TokenType.LEFT_PAREN }

val closeParensParser = one<Token> { it.type == TokenType.RIGHT_PAREN }

val groupingExpressionParser = takeMiddle(openParensParser, expressionParser, closeParensParser)

val debugExpressionParser = takeRight(one<Token> { it.type == TokenType.PRINT }, expressionParser).map { DebugExpression(it) }

fun createExpressionParser(): Parser<AbstractExpression, Token> {
    return choice(literalParser, letExpressionParser, groupingExpressionParser, debugExpressionParser)
}