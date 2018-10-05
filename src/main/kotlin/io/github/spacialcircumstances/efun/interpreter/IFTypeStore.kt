package io.github.spacialcircumstances.efun.interpreter

interface IFTypeStore {
    fun getType(key: String): FType<*>?
}