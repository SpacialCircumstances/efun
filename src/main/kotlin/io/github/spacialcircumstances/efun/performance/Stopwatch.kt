package io.github.spacialcircumstances.efun.performance

class Stopwatch {
    var running = false
    var last = 0L
    var currentTime = 0L

    fun start() {
        last = System.nanoTime()
        currentTime = 0
        running = true
    }

    fun stop() {
        currentTime = System.nanoTime() - last
        last = 0
        running = false
    }

    fun reset() {
        stop()
        start()
    }

    val elapsedTimeNanoseconds: Long
        get() {
            return if (running) {
                System.nanoTime() - last
            } else currentTime
        }
}