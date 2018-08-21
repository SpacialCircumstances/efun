package io.github.spacialcircumstances.efun.cli

import com.github.ajalt.clikt.core.CliktCommand
import io.github.spacialcircumstances.efun.Interpreter
import io.github.spacialcircumstances.efun.interpreter.defaultConfig

const val QUIT_COMMAND = ":q"

class Repl: CliktCommand() {
    override fun run() {
        val interpreter = Interpreter(defaultConfig())
        var running = true
        while (running) {
            print("> ")
            val code = readLine()
            code?.let {
                if (it == QUIT_COMMAND) {
                    running = false
                } else {
                    interpreter.interpret(it, { res -> println(res) }, { err -> println("Error: ${err.message}") })
                }
            }
        }
    }
}