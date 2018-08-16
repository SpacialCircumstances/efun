package io.github.spacialcircumstances.efun

import io.github.spacialcircumstances.efun.interpreter.*
import java.nio.file.Files
import java.nio.file.Paths

const val QUIT_COMMAND = ":q"

fun main(args: Array<String>) {
    val extPow = ExternalFunction2<Double, Double, Double>({ d1, d2 ->
        Math.pow(d1, d2)
    }, createFunctionType(listOf(TFloat, TFloat), TFloat))
    val external = ExternalBinding()
    external.externalFloat("pi", Math.PI)
    external.externalFunction("pow", extPow)
    val config = InterpreterConfig(external)
    val interpreter = Interpreter(config)
    if (args.size == 1) {
        val path = Paths.get(args[0])
        interpreter.interpret(String(Files.readAllBytes(path)), errorCallback = {
            throw IllegalStateException("Error executing script ${path.fileName}: ${it.message}", it)
        })
    } else if (args.isEmpty()) {
        var running = true
        while (running) {
            print("> ")
            val code = readLine()
            code?.let {
                if (it == QUIT_COMMAND) {
                    running = false
                } else {
                    interpreter.interpret(it, { res -> println(res) }, { err -> println("Error: ${err.message}") })
                }
            }
        }
    } else {
        println("Wrong number of arguments:")
        println("No args for REPL")
        println("1 arg to run file")
    }
}