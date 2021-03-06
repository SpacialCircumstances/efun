package io.github.spacialcircumstances.efun.cli.repl

interface ICommand {
    fun parse(line: String): Boolean
    fun execute(line: String, state: ReplState): ReplState
}