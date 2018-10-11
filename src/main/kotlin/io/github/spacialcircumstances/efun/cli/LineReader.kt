package io.github.spacialcircumstances.efun.cli

import java.util.*

class LineReader(private val scanner: Scanner) {
    fun readLine(): String? {
        return if (scanner.hasNextLine()) {
            scanner.nextLine()!!
        } else null
    }
}