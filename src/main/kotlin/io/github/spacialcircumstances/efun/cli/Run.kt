package io.github.spacialcircumstances.efun.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import io.github.spacialcircumstances.efun.Interpreter
import io.github.spacialcircumstances.efun.InterpreterState
import io.github.spacialcircumstances.efun.interpreter.defaultConfig
import java.lang.IllegalStateException
import java.nio.file.Files

class Run: CliktCommand(help = "Run the specified script file") {
    private val filename by argument().path(exists = true, fileOkay = true, folderOkay = false, readable = true)
    private val performanceMeasuring by option("-p", "--performance", help = "Log performance metrics").flag(default = false)

    override fun run() {
        val config = defaultConfig()
        val interpreterState = InterpreterState(config.createInterpreterContext(),
                config.createTypeContext(),
                { err -> throw IllegalStateException("Parser error: ${err.message}") },
                { err -> throw IllegalStateException("Type error: ${err.message}") },
                { err -> throw IllegalStateException("Runtime error: ${err.message}") },
                { _ -> })
        val interpreter = Interpreter(interpreterState)
        val code = String(Files.readAllBytes(filename))
        //TODO: Performance measuring
        interpreter.interpret(code)
    }
}