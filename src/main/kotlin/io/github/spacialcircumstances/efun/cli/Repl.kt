package io.github.spacialcircumstances.efun.cli

import com.github.ajalt.clikt.core.CliktCommand
import io.github.spacialcircumstances.efun.Interpreter
import io.github.spacialcircumstances.efun.interpreter.defaultConfig
import java.io.File
import java.nio.charset.Charset
import java.util.*

const val QUIT_COMMAND = ":q"
const val EVAL_COMMAND = ":eval"
const val TYPE_COMMAND = ":type"
const val LOAD_COMMAND = ":load"

class Repl: CliktCommand(help = "Run the REPL") {
    override fun run() {
        val scanner = Scanner(System.`in`)
        val lineReader = LineReader(scanner)
        val interpreter = Interpreter(defaultConfig())
        var running = true
        while (running) {
            val code = lineReader.readLine()
            if (code != null) {
                if (code == QUIT_COMMAND) {
                    running = false
                } else if (code.startsWith(TYPE_COMMAND)) {
                    val expr = code.removePrefix("$TYPE_COMMAND ")
                    //TODO: Only typecheck this to avoid side effects
                    interpreter.interpret(expr, { result -> println(result.type.name) }, { err -> println("Error: ${err.message}") })
                } else if (code.startsWith(LOAD_COMMAND)) {
                    val filename = code.removePrefix("$LOAD_COMMAND ")
                    val file = File(filename)
                    if (!file.exists()) {
                        println("Cannot load file ${file.path}")
                    } else {
                        val script = file.readText(Charset.defaultCharset())
                        interpreter.interpret(script, errorCallback = { err -> println("Error: ${err.message}") })
                    }
                } else {
                    val lines = mutableListOf(code)
                    while(true) {
                        val line = lineReader.readLine()
                        if (line != null) {
                            if (line == EVAL_COMMAND) {
                                break
                            } else {
                                lines.add(line)
                            }
                        }
                    }
                    val script = lines.joinToString("\n")
                    interpreter.interpret(script, { res -> println(res) }, { err -> println("Error: ${err.message}") })
                }
            }
        }
    }
}