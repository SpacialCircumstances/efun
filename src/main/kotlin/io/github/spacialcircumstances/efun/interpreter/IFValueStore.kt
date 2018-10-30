package io.github.spacialcircumstances.efun.interpreter

interface IFValueStore {
    operator fun get(key: String): ValueSlot?
}