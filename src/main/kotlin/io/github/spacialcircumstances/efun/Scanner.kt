package io.github.spacialcircumstances.efun

fun tokenize(code: String): List<Token> {
    var state = ScannerState(listOf(), code, 0, 0, 1)
    while(!isAtEnd(state)){
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
        '*' -> withToken(newState, token(newState, TokenType.STAR))
        '/' -> withToken(newState, token(newState, TokenType.SLASH))
        '+' -> withToken(newState, token(newState, TokenType.PLUS))
        '-' -> withToken(newState, token(newState, TokenType.MINUS))
        ',' -> withToken(newState, token(newState, TokenType.COMMA))
        '.' -> withToken(newState, token(newState, TokenType.DOT))
        ':' -> withToken(newState, token(newState, TokenType.COLON))
        '!' -> lookahead(newState, '=', TokenType.BANG_EQUAL, TokenType.BANG)
        '=' -> lookahead(newState, '=', TokenType.EQUAL_EQUAL, TokenType.EQUAL)
        '>' -> lookahead(newState, '=', TokenType.GREATER_EQUAL, TokenType.GREATER)
        '<' -> lookahead(newState, '=', TokenType.LESS_EQUAL, TokenType.LESS)
        '#' -> withToken(comment(newState), null)
        '\n' -> withToken(newState.copy(line = newState.line + 1), null)
        '"' -> string(newState)
        else -> {
            if (c.isWhitespace()) withToken(newState, null) else throw IllegalStateException()
        }
    }
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
    return if (peek(state) != '\n') comment(advance(state).first) else state
}

private fun peek(state: ScannerState): Char {
    return if (isAtEnd(state)) 0.toChar() else {
        state.source[state.current]
    }
}

private fun lookahead(state: ScannerState, char: Char, matchType: TokenType, nonMatchType: TokenType): ScannerState {
    return if (match(state, char)) {
        val (adv, _) = advance(state)
        withToken(adv, token(adv, matchType))
    } else {
        withToken(state, token(state, nonMatchType))
    }
}

private fun match(state: ScannerState, char: Char): Boolean {
    if (isAtEnd(state)) return false
    return state.source[state.current] != char
}

private fun token(state: ScannerState, type: TokenType): Token {
    return Token(type, state.source.substring(state.start, state.current), state.line, null)
}

private fun advance(state: ScannerState): Pair<ScannerState, Char> {
    return Pair(state.copy(current = state.current + 1), state.source[state.current])
}

data class ScannerState(val tokens: List<Token>, val source: String, val start: Int, val current: Int, val line: Int)