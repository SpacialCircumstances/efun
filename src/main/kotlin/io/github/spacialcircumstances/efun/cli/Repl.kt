package io.github.spacialcircumstances.efun.cli

import com.github.ajalt.clikt.core.CliktCommand
import io.github.spacialcircumstances.efun.Interpreter
import io.github.spacialcircumstances.efun.cli.repl.*
import io.github.spacialcircumstances.efun.interpreter.defaultConfig
import java.util.*

val commands = listOf(QuitCommand(), LoadCommand(), TypeCommand(), EvalCommand())

class Repl: CliktCommand(help = "Run the REPL") {
    override fun run() {
        val lineReader = LineReader(Scanner(System.`in`))
        val interpreter = Interpreter(defaultConfig())
        val context = ReplContext(interpreter, lineReader)
        var running = true
        while (running) {
            val line = lineReader.readLine()
            if (line != null) {
                val command = commands.find { it.parse(line) }
                if (command != null) {
                    running = command.execute(line, context)
                } else {
                    println("Error: No command found")
                }
            }
        }
    }
}