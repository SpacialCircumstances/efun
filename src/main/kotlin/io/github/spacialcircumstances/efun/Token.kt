package io.github.spacialcircumstances.efun

data class Token(val type: TokenType, val lexeme: String, val line: Int, val literal: Any?)