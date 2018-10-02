package io.github.spacialcircumstances.efun.cli

import com.github.ajalt.clikt.core.CliktCommand
import io.github.spacialcircumstances.efun.Interpreter
import io.github.spacialcircumstances.efun.interpreter.defaultConfig
import java.util.*

const val QUIT_COMMAND = ":q"
const val EVAL_COMMAND = ":eval"

class Repl: CliktCommand(help = "Run the REPL") {
    override fun run() {
        val scanner = Scanner(System.`in`)
        val lineReader = LineReader(scanner)
        val interpreter = Interpreter(defaultConfig())
        var running = true
        while (running) {
            val code = lineReader.readLine()
            if (code != null) {
                if (code == QUIT_COMMAND) {
                    running = false
                } else {
                    val lines = mutableListOf(code)
                    while(true) {
                        val line = lineReader.readLine()
                        if (line != null) {
                            if (line == EVAL_COMMAND) {
                                break
                            } else {
                                lines.add(line)
                            }
                        }
                    }
                    val script = lines.joinToString("\n")
                    interpreter.interpret(script, { res -> println(res) }, { err -> println("Error: ${err.message}") })
                }
            }
        }
    }
}