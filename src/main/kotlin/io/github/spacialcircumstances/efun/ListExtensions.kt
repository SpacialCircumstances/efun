package io.github.spacialcircumstances.efun

fun<T> List<T>.car(): T {
    return this.first()
}

fun<T> List<T>.carOrNull(): T? {
    return this.firstOrNull()
}

fun<T> List<T>.cdr(): List<T> {
    return if (this.size > 1) this.subList(1, this.size) else emptyList()
}

fun<T> List<T>.cdrOrNull(): List<T>? {
    val rest = this.cdr()
    return if (rest.isEmpty()) null else rest
}