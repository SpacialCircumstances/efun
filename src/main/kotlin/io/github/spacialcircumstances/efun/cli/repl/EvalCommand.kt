package io.github.spacialcircumstances.efun.cli.repl

import io.github.spacialcircumstances.efun.cli.repl.QuitCommand.Companion.QUIT_COMMAND

class EvalCommand: ICommand {
    companion object {
        const val EVAL_COMMAND = ":eval"
    }

    override fun parse(line: String): Boolean {
        return !line.startsWith(":")
    }

    override fun execute(line: String, context: ReplContext): Boolean {
        val lines = mutableListOf(line)
        while(true) {
            val nline = context.lineReader.readLine()
            if (nline != null) {
                if (nline == EVAL_COMMAND) {
                    break
                } else if (nline == QUIT_COMMAND) {
                    return false
                } else {
                    lines.add(nline)
                }
            }
        }
        val script = lines.joinToString("\n")
        context.interpreter.interpret(script, { res -> println(res) }, { err -> println("Error: ${err.message}") })
        return true
    }
}