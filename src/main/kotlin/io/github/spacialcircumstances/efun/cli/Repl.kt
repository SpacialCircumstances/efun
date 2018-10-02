package io.github.spacialcircumstances.efun.cli

import com.github.ajalt.clikt.core.CliktCommand
import io.github.spacialcircumstances.efun.Interpreter
import io.github.spacialcircumstances.efun.interpreter.defaultConfig
import java.util.*

const val QUIT_COMMAND = ":q"

class Repl: CliktCommand(help = "Run the REPL") {
    override fun run() {
        val scanner = Scanner(System.`in`)
        val lineReader = LineReader(scanner)
        val interpreter = Interpreter(defaultConfig())
        var running = true
        while (running) {
            val code = lineReader.readLines()
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