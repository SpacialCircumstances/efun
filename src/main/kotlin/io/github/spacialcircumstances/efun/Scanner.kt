package io.github.spacialcircumstances.efun

fun tokenize(code: String): List<Token> {
    val state = ScannerState(listOf(), code, 0, 0, 1)
    return state.tokens
}

private fun isAtEnd(state: ScannerState): Boolean {
    return state.current >= state.source.length
}

private fun scanToken(state: ScannerState): Pair<ScannerState, Token> {
    val (newState, c) = advance(state)
    return when(c) {
        '(' -> Pair(newState, token(newState, TokenType.LEFT_PAREN))
        ')' -> Pair(newState, token(newState, TokenType.RIGHT_PAREN))
        '{' -> Pair(newState, token(newState, TokenType.LEFT_BRACE))
        '}' -> Pair(newState, token(newState, TokenType.RIGHT_BRACE))
        '*' -> Pair(newState, token(newState, TokenType.STAR))
        '/' -> Pair(newState, token(newState, TokenType.SLASH))
        '+' -> Pair(newState, token(newState, TokenType.PLUS))
        '-' -> Pair(newState, token(newState, TokenType.MINUS))
        ',' -> Pair(newState, token(newState, TokenType.COMMA))
        '.' -> Pair(newState, token(newState, TokenType.DOT))
        ':' -> Pair(newState, token(newState, TokenType.COLON))
        '!' -> lookahead(newState, '=', TokenType.BANG_EQUAL, TokenType.BANG)
        '=' -> lookahead(newState, '=', TokenType.EQUAL_EQUAL, TokenType.EQUAL)
        '>' -> lookahead(newState, '=', TokenType.GREATER_EQUAL, TokenType.GREATER)
        '<' -> lookahead(newState, '=', TokenType.LESS_EQUAL, TokenType.LESS)
        '#' -> Pair(comment(newState), token(newState, TokenType.COMMENT))
    }
}

private fun comment(state: ScannerState): ScannerState {
    return if (peek(state) != '\n') comment(advance(state).first) else state
}

private fun peek(state: ScannerState): Char {
    return if (isAtEnd(state)) 0.toChar() else {
        state.source[state.current]
    }
}

private fun lookahead(state: ScannerState, char: Char, matchType: TokenType, nonMatchType: TokenType): Pair<ScannerState, Token> {
    if (match(state, char)) {
        val (adv, _) = advance(state)
        Pair(adv, token(adv, matchType))
    } else {
        Pair(state, token(state, nonMatchType))
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