package io.github.spacialcircumstances.efun

import com.github.ajalt.clikt.core.subcommands
import io.github.spacialcircumstances.efun.cli.Efun
import io.github.spacialcircumstances.efun.cli.Repl
import io.github.spacialcircumstances.efun.cli.Run

fun main(args: Array<String>) {
    Efun().subcommands(Repl(), Run()).main(args)
}