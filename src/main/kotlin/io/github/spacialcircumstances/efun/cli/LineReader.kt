package io.github.spacialcircumstances.efun.cli

import java.util.*

class LineReader(val scanner: Scanner) {
    fun readLines(): String {
        val lines = mutableListOf<String>()
        while(scanner.hasNextLine()) {
            val line = scanner.nextLine()
            if (line == ":ev") {
                break
            } else {
                lines.add(line)
            }
        }
        return lines.joinToString("\n")
    }
}