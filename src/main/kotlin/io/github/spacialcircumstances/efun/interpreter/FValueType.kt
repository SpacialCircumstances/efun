package io.github.spacialcircumstances.efun.interpreter

abstract class FType<out T> {
    abstract override fun equals(other: Any?): Boolean
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

val TVoid = VoidType()
val TInt = SimpleType<Long>("Int")
val TFloat = SimpleType<Double>("Float")
val TString = SimpleType<String>("String")
val TBool = SimpleType<Boolean>("Bool")
val TFunction = SimpleType<FFunction>("Function") //Temporary until better type system

fun checkNumeric(type: FType<*>): Boolean {
    return when(type) {
        TFloat -> true
        TInt -> true
        else -> false
    }
}