package io.github.spacialcircumstances.efun.cli.repl

class EvalCommand: ICommand {
    override fun parse(line: String): Boolean {
        return !line.startsWith(":")
    }

    override fun execute(line: String, context: ReplContext): Boolean {
        val lines = mutableListOf(line)
        while(true) {
            val nline = context.lineReader.readLine()
            if (nline != null) {
                if (nline == ":eval") {
                    break
                } else if (nline == ":q") {
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