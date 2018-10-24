package io.github.spacialcircumstances.efun

val identifierReplacements = mapOf(
        "let" to TokenType.LET,
        "if" to TokenType.IF,
        "else" to TokenType.ELSE,
        "foreach" to TokenType.FOREACH,
        "in" to TokenType.IN,
        "true" to TokenType.TRUE,
        "false" to TokenType.FALSE,
        "debug" to TokenType.DEBUG,
        "assert" to TokenType.ASSERT,
        "and" to TokenType.AND,
        "or" to TokenType.OR,
        "xor" to TokenType.XOR,
        "type" to TokenType.TYPE,
        "rec" to TokenType.REC,
        "uses" to TokenType.USES,
        "object" to TokenType.OBJECT
)

fun tokenize(code: String): List<Token> {
    var state = ScannerState(listOf(), code, 0, 0, 1)
    while (!isAtEnd(state)) {
        state = state.copy(start = state.current)
        state = scanToken(state)
    }
    return state.tokens
}

private fun isAtEnd(state: ScannerState): Boolean {
    return state.current >= state.source.length
}

private fun scanToken(state: ScannerState): ScannerState {
    val (newState, c) = advance(state)
    return when(c) {
        '(' -> withToken(newState, token(newState, TokenType.LEFT_PAREN))
        ')' -> withToken(newState, token(newState, TokenType.RIGHT_PAREN))
        '{' -> withToken(newState, token(newState, TokenType.LEFT_BRACE))
        '}' -> withToken(newState, token(newState, TokenType.RIGHT_BRACE))
        '[' -> withToken(newState, token(newState, TokenType.LEFT_BRACKET))
        ']' -> withToken(newState, token(newState, TokenType.RIGHT_BRACKET))
        '*' -> withToken(newState, token(newState, TokenType.STAR))
        '/' -> withToken(newState, token(newState, TokenType.SLASH))
        '+' -> withToken(newState, token(newState, TokenType.PLUS))
        '-' -> lookahead(newState, '>', TokenType.ARROW, TokenType.MINUS)
        ',' -> withToken(newState, token(newState, TokenType.COMMA))
        '.' -> withToken(newState, token(newState, TokenType.DOT))
        ':' -> withToken(newState, token(newState, TokenType.COLON))
        ';' -> withToken(newState, token(newState, TokenType.SEMICOLON))
        '!' -> lookahead(newState, '=', TokenType.BANG_EQUAL, TokenType.BANG)
        '=' -> lookahead(newState, '=', TokenType.EQUAL_EQUAL, TokenType.EQUAL)
        '>' -> lookahead(newState, '=', TokenType.GREATER_EQUAL, TokenType.GREATER)
        '<' -> lookahead(newState, '=', TokenType.LESS_EQUAL, TokenType.LESS)
        '#' -> withToken(comment(newState), null)
        '\n' -> withToken(newState.copy(line = newState.line + 1), null)
        '"' -> string(newState)
        else -> {
            when {
                c.isWhitespace() -> withToken(newState, null)
                isDigit(c) -> number(newState)
                isIdentifierStart(c) -> identifier(newState)
                else -> throw IllegalStateException()
            }
        }
    }
}

private fun identifier(state: ScannerState): ScannerState {
    var currentState = state
    while (isIdentifierPart(peek(currentState)))
        currentState = advance(currentState).first

    val identifier = currentState.source.substring(currentState.start, currentState.current)
    return withToken(currentState, Token(identifierReplacements[identifier] ?: TokenType.IDENTIFIER, identifier, currentState.line, identifier))
}

private fun isIdentifierPart(c: Char): Boolean {
    return isIdentifierStart(c) || isDigit(c) || c == '.'
}

private fun isIdentifierStart(c: Char): Boolean {
    return c in 'a'..'z' || c in 'A'..'Z' || c == '_'
}

private fun number(state: ScannerState): ScannerState {
    var currentState = state
    while (isDigit(peek(currentState))) {
        currentState = advance(currentState).first
    }

    if (peek(currentState) == '.' && isDigit(peekNext(currentState))) {
        currentState = advance(currentState).first
        while (isDigit(peek(currentState))) {
            currentState = advance(currentState).first
        }
    }

    val value = currentState.source.substring(currentState.start, currentState.current)
    return if (value.contains('.')) {
        withToken(currentState, Token(TokenType.FLOAT, value, currentState.line, value.toDouble()))
    } else {
        withToken(currentState, Token(TokenType.INTEGER, value, currentState.line, value.toLong()))
    }
}

private fun peekNext(state: ScannerState): Char {
    return if(isAtEnd(state) || isAtEnd(advance(state).first)) 0.toChar() else {
        state.source[state.current + 1]
    }
}

private fun isDigit(c: Char): Boolean {
    return c in '0'..'9'
}

private fun withToken(state: ScannerState, token: Token?): ScannerState {
    token?.let {
        return state.copy(tokens = (state.tokens + it))
    }
    return state
}

private fun string(state: ScannerState): ScannerState {
    var currentState = state
    while (peek(currentState) != '"' && !isAtEnd(currentState)) {
        if (peek(currentState) == '\n') currentState = currentState.copy(line = currentState.line + 1)
        currentState = advance(currentState).first
    }

    if (isAtEnd(currentState)) throw IllegalStateException()

    currentState = advance(currentState).first
    val str = currentState.source.substring(currentState.start + 1, currentState.current - 1)
    return withToken(currentState, Token(TokenType.STRING, "\"$str\"", currentState.line, str))
}

private fun comment(state: ScannerState): ScannerState {
    return if (peek(state) != '\n' && peek(state) != 0.toChar()) comment(advance(state).first) else state
}

private fun peek(state: ScannerState): Char {
    return if (isAtEnd(state)) 0.toChar() else {
        state.source[state.current]
    }
}

private fun lookahead(state: ScannerState, char: Char, matchType: TokenType, nonMatchType: TokenType): ScannerState {
    return if (match(state, char)) {
        val adv = advance(state).first
        withToken(adv, token(adv, matchType))
    } else {
        withToken(state, token(state, nonMatchType))
    }
}

private fun match(state: ScannerState, char: Char): Boolean {
    if (isAtEnd(state)) return false
    return state.source[state.current] == char
}

private fun token(state: ScannerState, type: TokenType): Token {
    return Token(type, state.source.substring(state.start, state.current), state.line, null)
}

private fun advance(state: ScannerState): Pair<ScannerState, Char> {
    return Pair(state.copy(current = state.current + 1), state.source[state.current])
}

data class ScannerState(val tokens: List<Token>, val source: String, val start: Int, val current: Int, val line: Int)