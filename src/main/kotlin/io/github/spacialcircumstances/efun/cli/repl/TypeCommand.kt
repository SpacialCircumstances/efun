package io.github.spacialcircumstances.efun.cli.repl

class TypeCommand: ICommand {
    override fun parse(line: String): Boolean {
        return line.startsWith(":type ")
    }

    override fun execute(line: String, context: ReplContext): Boolean {
        val expr = line.removePrefix(":type ")
        //TODO: Only typecheck this to avoid side effects
        context.interpreter.interpret(expr, { result -> println(result.type.name) }, { err -> println("Error: ${err.message}") })
        return true
    }
}