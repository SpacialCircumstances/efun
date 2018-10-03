package io.github.spacialcircumstances.efun.cli.repl

class QuitCommand: ICommand {
    companion object {
        const val QUIT_COMMAND = ":q"
    }

    override fun execute(line: String, context: ReplContext): Boolean {
        return false
    }

    override fun parse(line: String): Boolean {
        return line == QUIT_COMMAND
    }
}