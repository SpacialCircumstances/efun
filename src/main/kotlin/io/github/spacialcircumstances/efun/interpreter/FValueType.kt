package io.github.spacialcircumstances.efun.interpreter

import io.github.spacialcircumstances.efun.RuntimeError

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
        throw RuntimeError("Cannot use value of Type Void in expression")
    }
}

class FunctionType(val inType: FType<*>, val outType: FType<*>): FType<IFunctionPointer>() {
    override val name: String = "(${inType.name} -> ${outType.name})"

    override fun castValue(value: FValue): IFunctionPointer {
        return value.value as IFunctionPointer
    }

    override fun equals(other: Any?): Boolean {
        return if (other is FunctionType) {
            (other.inType == inType) && (other.outType == outType)
        } else false
    }
}

class EnumType(val instances: List<EnumInstance>): FType<EnumInstance>() {
    override val name: String = "Enum of: ${instances.joinToString()}"

    override fun castValue(value: FValue): EnumInstance {
        return value.value as EnumInstance
    }

    override fun equals(other: Any?): Boolean {
        return if (other is EnumType) {
            (name == other.name) && (instances == other.instances)
        } else false
    }
}

class RecordType(val definition: RecordDefinition): FType<RecordInstance>() {
    override fun equals(other: Any?): Boolean {
        return if (other is RecordType) {
            other.definition == definition
        } else false
    }

    override val name: String = "Record ${definition.name}"

    override fun castValue(value: FValue): RecordInstance {
        return value as RecordInstance
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

val TVoid = VoidType()
val TInt = SimpleType<Long>("Int")
val TFloat = SimpleType<Double>("Float")
val TString = SimpleType<String>("String")
val TBool = SimpleType<Boolean>("Bool")
val TType = SimpleType<FType<*>>("Type")