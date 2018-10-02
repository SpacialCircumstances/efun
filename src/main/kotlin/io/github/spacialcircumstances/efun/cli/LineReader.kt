package io.github.spacialcircumstances.efun.cli

import java.util.*

class LineReader(val scanner: Scanner) {
    fun readLine(): String? {
        return if (scanner.hasNextLine()) {
            scanner.nextLine()!!
        } else null
    }
}