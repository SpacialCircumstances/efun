package io.github.spacialcircumstances.efun.interpreter

abstract class FType<out T> {
    abstract override operator fun equals(other: Any?): Boolean
    abstract val name: String
    abstract fun castValue(value: FValue): T

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun toString(): String {
        return name
    }
}

class SimpleType<out T>(override val name: String): FType<T>() {
    override fun castValue(value: FValue): T {
        @Suppress("UNCHECKED_CAST")
        return value.value as T
    }

    override fun equals(other: Any?): Boolean {
        return if (other is FType<*>) {
            other.name == name
        } else false
    }
}

class VoidType: FType<Unit>() {
    override fun equals(other: Any?): Boolean {
        return other is VoidType
    }

    override val name: String = "Void"

    override fun castValue(value: FValue) {
        throw IllegalStateException("Cannot use value of Type Void in expression")
    }
}

class AnyType: FType<Any>() {
    override fun equals(other: Any?): Boolean {
        return true
    }

    override val name: String = "Any"

    override fun castValue(value: FValue): Any {
        return value.type.castValue(value)!!
    }

}

class FunctionType(val inType: FType<*>, val outType: FType<*>): FType<FunctionPointer>() {
    override val name: String = "(${inType.name} -> ${outType.name})"

    override fun castValue(value: FValue): FunctionPointer {
        return value.value as FunctionPointer
    }

    override fun equals(other: Any?): Boolean {
        return if (other is FunctionType) {
            (other.inType == inType) && (other.outType == outType)
        } else false
    }
}

val TVoid = VoidType()
val TInt = SimpleType<Long>("Int")
val TFloat = SimpleType<Double>("Float")
val TString = SimpleType<String>("String")
val TBool = SimpleType<Boolean>("Bool")

fun checkNumeric(type: FType<*>): Boolean {
    return when(type) {
        TFloat -> true
        TInt -> true
        else -> false
    }
}