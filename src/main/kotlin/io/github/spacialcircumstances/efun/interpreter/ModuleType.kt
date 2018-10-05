package io.github.spacialcircumstances.efun.interpreter

class ModuleType(override val name: String, private val module: Module): FType<Module>() {
    override fun equals(other: Any?): Boolean {
        return if (other is ModuleType) {
            other.name == name //TODO: Memberwise equality
        } else false
    }

    override fun castValue(value: FValue): Module {
        return value.value as Module
    }

    override val subTypeStore: IFTypeStore? = module
}