package io.github.spacialcircumstances.efun

class Interpreter {
    val scanner = Scanner()
    val parser = Parser()

    fun interpret(code: String) {
        val tokens = scanner.tokenize(code)
    }
}