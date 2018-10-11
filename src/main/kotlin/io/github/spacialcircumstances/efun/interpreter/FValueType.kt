package io.github.spacialcircumstances.efun.interpreter

import io.github.spacialcircumstances.efun.RuntimeError
import java.lang.IllegalStateException
import javax.naming.OperationNotSupportedException

abstract class FType<out T> {
    abstract override operator fun equals(other: Any?): Boolean
    abstract val name: String
    abstract fun castValue(value: FValue): T
    abstract val subTypeStore: IFTypeStore?

    override fun toString(): String {
        return name
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (subTypeStore?.hashCode() ?: 0)
        return result
    }
}

class SimpleType<out T>(override val name: String): FType<T>() {
    override val subTypeStore: IFTypeStore? = null

    override fun castValue(value: FValue): T {
        @Suppress("UNCHECKED_CAST")
        return value.value as T
    }

    override fun equals(other: Any?): Boolean {
        return if (other is FType<*>) {
            other.name == name
        } else false
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + (subTypeStore?.hashCode() ?: 0)
        return result
    }
}

class VoidType: FType<Unit>() {
    override val subTypeStore: IFTypeStore? = null

    override fun equals(other: Any?): Boolean {
        return other is VoidType
    }

    override val name: String = "Void"

    override fun castValue(value: FValue) {
        throw RuntimeError("Cannot use value of Type Void in expression")
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (subTypeStore?.hashCode() ?: 0)
        result = 31 * result + name.hashCode()
        return result
    }
}

class FunctionType(val inType: FType<*>, val outType: FType<*>): FType<IFunctionPointer>() {
    override val subTypeStore: IFTypeStore? = null
    override val name: String = "(${inType.name} -> ${outType.name})"

    override fun castValue(value: FValue): IFunctionPointer {
        return value.value as IFunctionPointer
    }

    override fun equals(other: Any?): Boolean {
        return if (other is FunctionType) {
            (other.inType == inType) && (other.outType == outType)
        } else false
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + inType.hashCode()
        result = 31 * result + outType.hashCode()
        result = 31 * result + (subTypeStore?.hashCode() ?: 0)
        result = 31 * result + name.hashCode()
        return result
    }
}

fun getReturnType(type: FunctionType, maxDepth: Int): FType<*>? {
    var currentFp = type
    var result: FType<*>? = null
    for (i in 0 until maxDepth) {
        val out = currentFp.outType
        result = out
        if (out is FunctionType) {
            currentFp = out
        }
    }
    return result
}

class TypeType(val type: FType<*>): FType<FType<*>>() {
    override fun equals(other: Any?): Boolean {
        return if (other is TypeType) {
            other.type == type
        } else false
    }

    override val name: String = "Type ${type.name}"

    override fun castValue(value: FValue): FType<*> {
        throw IllegalStateException()
    }

    override val subTypeStore: IFTypeStore? = type.subTypeStore
}

val TVoid = VoidType()
val TInt = SimpleType<Long>("Int")
val TFloat = SimpleType<Double>("Float")
val TString = SimpleType<String>("String")
val TBool = SimpleType<Boolean>("Bool")
val TType = SimpleType<FType<*>>("Type")