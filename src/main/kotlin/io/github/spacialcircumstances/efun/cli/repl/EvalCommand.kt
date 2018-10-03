package io.github.spacialcircumstances.efun.cli.repl

import io.github.spacialcircumstances.efun.Interpreter
import io.github.spacialcircumstances.efun.cli.repl.QuitCommand.Companion.QUIT_COMMAND

class EvalCommand: ICommand {
    companion object {
        const val EVAL_COMMAND = ":eval"
    }

    override fun parse(line: String): Boolean {
        return !line.startsWith(":")
    }

    override fun execute(line: String, state: ReplState): ReplState {
        val lines = mutableListOf(line)
        while(true) {
            val nline = state.lineReader.readLine()
            if (nline != null) {
                if (nline == EVAL_COMMAND) {
                    break
                } else if (nline == QUIT_COMMAND) {
                    return state.copy(running = false)
                } else {
                    lines.add(nline)
                }
            }
        }
        val script = lines.joinToString("\n")
        val interpreter = Interpreter(state.interpreterState)
        interpreter.interpret(script)
        return state
    }
}