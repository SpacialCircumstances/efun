package io.github.spacialcircumstances.efun.interpreter

class EnumInstance(val name: String, val id: Int) {
    override fun equals(other: Any?): Boolean {
        return if (other is EnumInstance) {
            (other.id == id) && (other.name == name)
        } else false
    }

    override fun toString(): String {
        return "Enum ($id): $name"
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + id
        return result
    }
}