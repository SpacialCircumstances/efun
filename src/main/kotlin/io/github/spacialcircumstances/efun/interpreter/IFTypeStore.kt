package io.github.spacialcircumstances.efun.interpreter

interface IFTypeStore {
    operator fun get(key: String): FType<*>?
}