package io.github.spacialcircumstances.efun.cli.repl

import io.github.spacialcircumstances.efun.parse

class TypeCommand: ICommand {
    companion object {
        const val TYPE_COMMAND = ":type "
    }
    override fun parse(line: String): Boolean {
        return line.startsWith(TYPE_COMMAND)
    }

    override fun execute(line: String, state: ReplState): ReplState {
        val expr = line.removePrefix(TYPE_COMMAND)
        val parsedAst = parse(expr, state.interpreterState)
        if (parsedAst != null) {
            val expression = parsedAst.singleOrNull()
            if (expression != null) {
                println(expression.guessType(state.interpreterState.typesContext))
            } else {
                println("Only single expressions supported")
            }
        } //Parser errors should be already printed
        return state
    }
}