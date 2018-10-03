package io.github.spacialcircumstances.efun.cli.repl

class TypeCommand: ICommand {
    companion object {
        val TYPE_COMMAND = ":type "
    }
    override fun parse(line: String): Boolean {
        return line.startsWith(TYPE_COMMAND)
    }

    override fun execute(line: String, context: ReplContext): Boolean {
        val expr = line.removePrefix(TYPE_COMMAND)
        //TODO: Only typecheck this to avoid side effects
        context.interpreter.interpret(expr, { result -> println(result.type.name) }, { err -> println("Error: ${err.message}") })
        return true
    }
}