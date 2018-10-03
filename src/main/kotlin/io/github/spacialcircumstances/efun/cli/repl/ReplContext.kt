package io.github.spacialcircumstances.efun.cli.repl

import io.github.spacialcircumstances.efun.Interpreter
import io.github.spacialcircumstances.efun.cli.LineReader

data class ReplContext(val interpreter: Interpreter, val lineReader: LineReader)
