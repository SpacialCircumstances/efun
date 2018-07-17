package io.github.spacialcircumstances.efun

import io.github.spacialcircumstances.efun.expressions.*
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

val valueProducingExpressionParser = lazy(::createValueProducingExpressionParser)

val literalParser = choice(stringLiteralParser, intLiteralParser, floatLiteralParser, trueLiteralParser, falseLiteralParser)

val arrowParser = one<Token> { it.type == TokenType.ARROW }

val nameParser = oneWith<String, Token>({ it.type == TokenType.IDENTIFIER }) {
    it.literal as String
}

val letNameParser = takeMiddle(one { it.type == TokenType.LET },
                                nameParser,
                                one { it.type == TokenType.EQUAL })

val letExpressionParser = letNameParser.andThen(valueProducingExpressionParser).map {
    LetExpression(it.second, it.first)
}

val unaryOperatorParser: Parser<String, Token> = choice<Token, Token>(
        one { it.type == TokenType.MINUS },
        one { it.type == TokenType.BANG }
).map { it.lexeme }

val binaryOperatorParser: Parser<Token, Token> = choice(
        one { it.type == TokenType.MINUS },
        one { it.type == TokenType.PLUS },
        one { it.type == TokenType.STAR },
        one { it.type == TokenType.SLASH },
        one { it.type == TokenType.LESS },
        one { it.type == TokenType.LESS_EQUAL },
        one { it.type == TokenType.GREATER },
        one { it.type == TokenType.GREATER_EQUAL },
        one { it.type == TokenType.BANG_EQUAL },
        one { it.type == TokenType.EQUAL_EQUAL }
)

val openParensParser = one<Token> { it.type == TokenType.LEFT_PAREN }

val closeParensParser = one<Token> { it.type == TokenType.RIGHT_PAREN }

val groupingExpressionParser = takeMiddle(openParensParser, valueProducingExpressionParser, closeParensParser).map {
    GroupingExpression(it)
}

val commaParser = one<Token> { it.type == TokenType.COMMA }

val debugExpressionParser = takeRight(one { it.type == TokenType.PRINT }, expressionParser).map { DebugExpression(it) }

val argumentsParser = takeMiddle(openParensParser, valueProducingExpressionParser.separator(commaParser), closeParensParser)

val variableExpressionParser = oneWith<VariableExpression, Token>({ it.type == TokenType.IDENTIFIER }) { VariableExpression(it.lexeme) }

val callableExpressionParser = choice(groupingExpressionParser, literalParser, variableExpressionParser)

val functionCallParser = callableExpressionParser.andThen(argumentsParser).map {
    FunctionCallExpression(it.first, it.second)
}

val operatorExpressionParser = choice(functionCallParser, variableExpressionParser, literalParser, groupingExpressionParser)

val binaryExpressionParser = operatorExpressionParser.andThen(binaryOperatorParser).andThen(operatorExpressionParser).map {
    BinaryExpression(it.first.first, it.first.second, it.second)
}

val unaryExpressionParser = unaryOperatorParser.andThen(operatorExpressionParser).map { UnaryExpression(it.second, it.first) }

val typeNameParser = oneWith<FValueType, Token>({ it.type == TokenType.IDENTIFIER }) {
    when(it.lexeme) {
        "Bool" -> FValueType.Bool
        "String" -> FValueType.String
        "Int" -> FValueType.Int
        "Float" -> FValueType.Float
        else -> FValueType.Void
    }
}

val blockStartParser = one<Token> { it.type == TokenType.LEFT_BRACE }
val blockEndParser = one<Token> { it.type == TokenType.RIGHT_BRACE }
val blockArgumentParser = oneWith<String, Token>({ it.type == TokenType.IDENTIFIER }) { it.lexeme }
val singleArgParser = blockArgumentParser.andIgnoreResult(one { it.type == TokenType.COLON }).andThen(typeNameParser).andIgnoreResult(arrowParser)
val blockArgumentsParser = singleArgParser.many().map { types ->
    types.associate { it }
}

val blockBodyParser = expressionParser.many()
val blockParser = takeMiddle(blockStartParser, blockArgumentsParser.andThen(blockBodyParser), blockEndParser).map {
    BlockExpression(it.first, it.second)
}

fun createValueProducingExpressionParser(): Parser<AbstractExpression, Token> {
    return choice(binaryExpressionParser, unaryExpressionParser, literalParser, groupingExpressionParser, functionCallParser, variableExpressionParser, blockParser)
}

fun createExpressionParser(): Parser<AbstractExpression, Token> {
    return choice(binaryExpressionParser, unaryExpressionParser, literalParser, debugExpressionParser, groupingExpressionParser, letExpressionParser, functionCallParser, variableExpressionParser, blockParser)
}

val programParser = expressionParser.many()