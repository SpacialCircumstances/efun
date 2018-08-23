package io.github.spacialcircumstances.efun

import io.github.spacialcircumstances.efun.expressions.*
import io.github.spacialcircumstances.efun.interpreter.*
import io.github.spacialcircumstances.efun.parser.*

val stringLiteralParser = oneWith<LiteralExpression, Token>({ it.type == TokenType.STRING }) {
    LiteralExpression(Pair(SimplePlaceholderType("String"), it.literal!!))
}

val intLiteralParser = oneWith<LiteralExpression, Token>({ it.type == TokenType.INTEGER }) {
    LiteralExpression(Pair(SimplePlaceholderType("Int"), it.literal!!))
}

val floatLiteralParser = oneWith<LiteralExpression, Token>({ it.type == TokenType.FLOAT }) {
    LiteralExpression(Pair(SimplePlaceholderType("Float"), it.literal!!))
}

val trueLiteralParser = oneWith<LiteralExpression, Token>({ it.type == TokenType.TRUE }) {
    LiteralExpression(Pair(SimplePlaceholderType("Bool"), true))
}

val falseLiteralParser = oneWith<LiteralExpression, Token>({ it.type == TokenType.FALSE }) {
    LiteralExpression(Pair(SimplePlaceholderType("Bool"), false))
}

val semicolonParser = one<Token> { it.type == TokenType.SEMICOLON }

val expressionParser = lazy(::createExpressionParser)

val valueProducingExpressionParser = lazy(::createValueProducingExpressionParser)

val literalParser = choice(stringLiteralParser, intLiteralParser, floatLiteralParser, trueLiteralParser, falseLiteralParser)

val arrowParser = one<Token> { it.type == TokenType.ARROW }

val nameParser = oneWith<String, Token>({ it.type == TokenType.IDENTIFIER }) {
    it.literal as String
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
        one { it.type == TokenType.EQUAL_EQUAL },
        one { it.type == TokenType.OR },
        one { it.type == TokenType.AND },
        one { it.type == TokenType.XOR },
        one { it.type == TokenType.IS }
)

val openParensParser = one<Token> { it.type == TokenType.LEFT_PAREN }

val closeParensParser = one<Token> { it.type == TokenType.RIGHT_PAREN }

val groupingExpressionParser = takeMiddle(openParensParser, valueProducingExpressionParser, closeParensParser).map {
    GroupingExpression(it)
}

val commaParser = one<Token> { it.type == TokenType.COMMA }

val debugExpressionParser = takeRight(one { it.type == TokenType.DEBUG }, expressionParser).map { DebugExpression(it) }

val argumentsParser = takeMiddle(openParensParser, valueProducingExpressionParser.separator(commaParser), closeParensParser)

val variableExpressionParser = oneWith<VariableExpression, Token>({ it.type == TokenType.IDENTIFIER }) { VariableExpression(it.lexeme) }

val callableExpressionParser = choice(groupingExpressionParser, literalParser, variableExpressionParser)

val functionCallParser = callableExpressionParser.andThen(argumentsParser).map {
    FunctionCallExpression(it.first, it.second)
}

val operatorExpressionParser = lazy(::createOperatorExpressionParser)

val binaryExpressionParser = operatorExpressionParser.andThen(binaryOperatorParser).andThen(operatorExpressionParser).map {
    BinaryExpression(it.first.first, it.first.second, it.second)
}

val unaryExpressionParser = unaryOperatorParser.andThen(operatorExpressionParser).map { UnaryExpression(it.second, it.first) }

val typeNameParser = oneWith<PlaceholderType, Token>({ it.type == TokenType.IDENTIFIER }) {
    SimplePlaceholderType(it.lexeme)
}

val typeFunctionParser = takeMiddle(openParensParser, typeNameParser.separator(arrowParser), closeParensParser).map {
    val returnType = it.last()
    val paramTypes = it.take(it.size - 1)
    FunctionPlaceholderType(paramTypes, returnType)
}

val typeParser = typeFunctionParser.orElse(typeNameParser)

val blockStartParser = one<Token> { it.type == TokenType.LEFT_BRACE }
val blockEndParser = one<Token> { it.type == TokenType.RIGHT_BRACE }
val blockArgumentParser = oneWith<String, Token>({ it.type == TokenType.IDENTIFIER }) { it.lexeme }
val singleArgParser = blockArgumentParser.andIgnoreResult(one { it.type == TokenType.COLON }).andThen(typeParser).andIgnoreResult(arrowParser)
val blockArgumentsParser = singleArgParser.many()

val blockBodyParser = expressionParser.many()
val blockParser = takeMiddle(blockStartParser, blockArgumentsParser.andThen(blockBodyParser), blockEndParser).map {
    BlockExpression(it.first, it.second)
}

val typeExprNameParser = takeMiddle(one { it.type == TokenType.TYPE }, oneWith<String, Token>({ it.type == TokenType.IDENTIFIER }, { it.lexeme }), one { it.type == TokenType.EQUAL })

val enumValuesParser = oneWith<String, Token>({ it.type == TokenType.IDENTIFIER }, { it.lexeme }).separator(commaParser).andIgnoreResult(semicolonParser)

val enumDefinitionParser = typeExprNameParser.andIgnoreResult(one { it.type == TokenType.ENUM }).andThen(enumValuesParser).map {
    TypeExpression(it.first, EnumTypeExpression(it.second))
}

val leftBraceParser = one<Token> { it.type == TokenType.LEFT_BRACE }

val rightBraceParser = one<Token> { it.type == TokenType.RIGHT_BRACE }

val colonParser = one<Token> { it.type == TokenType.COLON }

val recordValueParser = oneWith<String, Token>({ it.type == TokenType.IDENTIFIER }) { it.lexeme }.andIgnoreResult(colonParser).andThen(typeParser)

val recordBodyParser = takeMiddle(leftBraceParser, recordValueParser.separator(commaParser), rightBraceParser)

val recordDefinitionParser = typeExprNameParser.andIgnoreResult(one { it.type == TokenType.RECORD }).andThen(recordBodyParser).map {
    TypeExpression(it.first, RecordTypeExpression(it.first, it.second))
}

val constructorPairParser = nameParser.andIgnoreResult(colonParser).andThen(valueProducingExpressionParser)

val constructorBodyParser = takeMiddle(leftBraceParser, constructorPairParser.separator(commaParser), rightBraceParser)

val constructorParser = takeRight(one { it.type == TokenType.STAR }, nameParser).andThen(constructorBodyParser).map {
    ConstructorExpression(it.first, it.second)
}

val letNameParser = takeMiddle(one { it.type == TokenType.LET },
        nameParser,
        one { it.type == TokenType.EQUAL })

val recTypeParser = takeRight(one<Token> { it.type == TokenType.REC }.andThen(arrowParser), typeParser)

val letRecNameParser = takeRight(one { it.type == TokenType.LET }, recTypeParser).andThen(nameParser).andIgnoreResult(one { it.type == TokenType.EQUAL })

val letRecExpressionParser = letRecNameParser.andThen(valueProducingExpressionParser).map {
    RecLetExpression(it.first.second, it.first.first, it.second as BlockExpression)
}

val letExpressionParser = letNameParser.andThen(valueProducingExpressionParser).map {
    LetExpression(it.second, it.first)
}

val noArgBlockParser = takeMiddle(blockStartParser, blockBodyParser, blockEndParser)

val elseParser = takeRight(one { it.type == TokenType.ELSE }, noArgBlockParser)

val ifExpressionParser = takeRight(one { it.type == TokenType.IF }, valueProducingExpressionParser.andThen(noArgBlockParser).andThen(elseParser.optional())).map {
    val elseExpressions = it.second.singleOrNull()
    IfExpression(it.first.first, BlockExpression(emptyList(), it.first.second), if (elseExpressions == null) null else BlockExpression(emptyList(), elseExpressions))
}

val assertStatementParser = takeRight(one { it.type == TokenType.ASSERT }, valueProducingExpressionParser).map {
    AssertExpression(it)
}

fun createOperatorExpressionParser(): Parser<AbstractExpression, Token> {
    return choice(functionCallParser, variableExpressionParser, literalParser, groupingExpressionParser, unaryExpressionParser)
}

fun createValueProducingExpressionParser(): Parser<AbstractExpression, Token> {
    return choice(binaryExpressionParser, unaryExpressionParser, literalParser, groupingExpressionParser, functionCallParser, variableExpressionParser, blockParser, ifExpressionParser, constructorParser)
}

fun createExpressionParser(): Parser<AbstractExpression, Token> {
    return choice(binaryExpressionParser, unaryExpressionParser, literalParser, debugExpressionParser, groupingExpressionParser, letRecExpressionParser, letExpressionParser, enumDefinitionParser, recordDefinitionParser, functionCallParser, variableExpressionParser, ifExpressionParser, constructorParser, assertStatementParser, blockParser)
}

val programParser = expressionParser.many()