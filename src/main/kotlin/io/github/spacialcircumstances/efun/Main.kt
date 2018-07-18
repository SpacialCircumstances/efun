package io.github.spacialcircumstances.efun

import java.nio.file.Files
import java.nio.file.Paths

const val QUIT_COMMAND = ":q"

fun main(args: Array<String>) {
    val interpreter = Interpreter()
    if (args.size == 1) {
        interpreter.interpret(String(Files.readAllBytes(Paths.get(args[0]))))
    } else if (args.isEmpty()) {
        var running = true
        while (running) {
            print("> ")
            val code = readLine()
            code?.let {
                if (it == QUIT_COMMAND) {
                    running = false
                } else {
                    interpreter.interpret(it, { res -> println(res) })
                }
            }
        }
    } else {
        println("Wrong number of arguments:")
        println("No args for REPL")
        println("1 arg to run file")
    }
}