package io.github.spacialcircumstances.efun.cli.repl

import io.github.spacialcircumstances.efun.Interpreter
import java.io.File
import java.nio.charset.Charset

class LoadCommand: ICommand {
    companion object {
        const val LOAD_COMMAND = ":load "
    }

    override fun execute(line: String, state: ReplState): ReplState {
        val filename = line.removePrefix(LOAD_COMMAND)
        val file = File(filename)
        if (!file.exists()) {
            println("Cannot load file ${file.path}")
        } else {
            val script = file.readText(Charset.defaultCharset())
            val interpreter = Interpreter(state.interpreterState)
            interpreter.interpret(script)
        }
        return state
    }

    override fun parse(line: String): Boolean {
        return line.startsWith(LOAD_COMMAND)
    }
}