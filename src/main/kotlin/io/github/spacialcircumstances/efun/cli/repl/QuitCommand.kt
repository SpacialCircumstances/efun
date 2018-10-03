package io.github.spacialcircumstances.efun.cli.repl

class QuitCommand: ICommand {
    override fun execute(line: String, context: ReplContext): Boolean {
        return false
    }

    override fun parse(line: String): Boolean {
        return line == ":q"
    }
}