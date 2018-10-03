package io.github.spacialcircumstances.efun.cli.repl

import java.io.File
import java.nio.charset.Charset

class LoadCommand: ICommand {
    companion object {
        const val LOAD_COMMAND = ":load "
    }

    override fun execute(line: String, context: ReplContext): Boolean {
        val filename = line.removePrefix(LOAD_COMMAND)
        val file = File(filename)
        if (!file.exists()) {
            println("Cannot load file ${file.path}")
        } else {
            val script = file.readText(Charset.defaultCharset())
            context.interpreter.interpret(script, errorCallback = { err -> println("Error: ${err.message}") })
        }
        return true
    }

    override fun parse(line: String): Boolean {
        return line.startsWith(LOAD_COMMAND)
    }
}