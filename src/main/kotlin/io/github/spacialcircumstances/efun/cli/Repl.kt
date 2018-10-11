package io.github.spacialcircumstances.efun.cli

import com.github.ajalt.clikt.core.CliktCommand
import io.github.spacialcircumstances.efun.InterpreterState
import io.github.spacialcircumstances.efun.cli.repl.*
import io.github.spacialcircumstances.efun.interpreter.defaultConfig
import java.util.*

val commands = listOf(QuitCommand(), LoadCommand(), TypeCommand(), EvalCommand())

class Repl: CliktCommand(help = "Run the REPL") {
    override fun run() {
        val lineReader = LineReader(Scanner(System.`in`))
        val config = defaultConfig()
        val interpreterState = InterpreterState(config.createInterpreterContext(),
                config.createTypeContext(),
                { err -> println("Parser error: ${err.message}") },
                { err -> println("Type error: ${err.message}") },
                {err -> println("Runtime error: ${err.message}") },
                { value -> println(value) })
        var context = ReplState(true, interpreterState, lineReader)
        var running = true
        while (running) {
            val line = lineReader.readLine()
            if (line != null) {
                val command = commands.find { it.parse(line) }
                if (command != null) {
                    context = command.execute(line, context)
                    running = context.running
                } else {
                    println("Error: No command found")
                }
            }
        }
    }
}