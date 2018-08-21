package io.github.spacialcircumstances.efun.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.path
import io.github.spacialcircumstances.efun.Interpreter
import io.github.spacialcircumstances.efun.interpreter.defaultConfig
import java.nio.file.Files

class Run: CliktCommand(help = "Run the specified script file") {
    val filename by argument().path(exists = true, fileOkay = true, folderOkay = false, readable = true)
    val performanceMeasuring by option("-p", "--performance", help = "Log performance metrics").flag(default = false)

    override fun run() {
        val interpreter = Interpreter(defaultConfig())
        val code = String(Files.readAllBytes(filename))
        if (performanceMeasuring) {
            interpreter.interpret(code, errorCallback = {
                throw IllegalStateException("Error executing script ${filename.fileName}: ${it.message}", it)
            }, executionLogCallback = { s, t -> println("##### $s: ${t / 1000000}ms") })
        } else {
            interpreter.interpret(code, errorCallback = {
                throw IllegalStateException("Error executing script ${filename.fileName}: ${it.message}", it)
            })
        }
    }
}