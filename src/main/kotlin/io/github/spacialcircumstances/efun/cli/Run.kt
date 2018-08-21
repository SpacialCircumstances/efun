package io.github.spacialcircumstances.efun.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import io.github.spacialcircumstances.efun.Interpreter
import io.github.spacialcircumstances.efun.interpreter.defaultConfig
import java.nio.file.Files
import java.nio.file.Paths

class Run: CliktCommand() {
    val file by argument()

    override fun run() {
        val interpreter = Interpreter(defaultConfig())
        val path = Paths.get(file)
        interpreter.interpret(String(Files.readAllBytes(path)), errorCallback = {
            throw IllegalStateException("Error executing script ${path.fileName}: ${it.message}", it)
        }, executionLogCallback = { s, t -> println("##### $s: ${t/1000000}ms")})
    }
}