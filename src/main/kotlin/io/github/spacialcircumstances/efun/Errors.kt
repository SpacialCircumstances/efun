package io.github.spacialcircumstances.efun

class RuntimeError(message: String, cause: Throwable? = null): Exception(message, cause)

class TypeError(message: String): Exception(message)