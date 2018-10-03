package io.github.spacialcircumstances.efun.cli.repl

import io.github.spacialcircumstances.efun.InterpreterState
import io.github.spacialcircumstances.efun.cli.LineReader

data class ReplState(val running: Boolean, val interpreterState: InterpreterState, val lineReader: LineReader)
