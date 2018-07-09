package io.github.spacialcircumstances.efun

fun tokenize(code: String): List<Token> {
    val state = ScannerState(listOf(), code, 0, 0, 1)
    return state.tokens
}

private fun isAtEnd(state: ScannerState): Boolean {
    return state.current >= state.source.length
}

data class ScannerState(val tokens: List<Token>, val source: String, val start: Int, val current: Int, val line: Int)