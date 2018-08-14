package io.github.spacialcircumstances.efun.expressions.binary

import io.github.spacialcircumstances.efun.RuntimeError
import io.github.spacialcircumstances.efun.TypeError
import io.github.spacialcircumstances.efun.interpreter.FType
import io.github.spacialcircumstances.efun.interpreter.FValue

abstract class BinaryOperator {
    abstract fun typecheck(l: FType<*>, r: FType<*>): FType<*>
    abstract fun compute(l: FValue, r: FValue): FValue
}

abstract class SimpleBinaryOperator<L, R, O>(val lType: FType<L>, val rType: FType<R>, val outType: FType<O>): BinaryOperator() {
    override fun typecheck(l: FType<*>, r: FType<*>): FType<*> {
        if (l != lType) throw TypeError("Expected type $lType, but got $l")
        if (r != rType) throw TypeError("Expected type $rType, but got $r")
        return outType
    }

    abstract fun computeBinary(left: L, right: R): O

    override fun compute(l: FValue, r: FValue): FValue {
        val lv = lType.castValue(l)
        val rv = rType.castValue(r)
        val result = computeBinary(lv, rv)
        return FValue(outType, result)
    }
}

abstract class MultiOperator(val operatorByType: Map<Pair<FType<*>, FType<*>>, BinaryOperator>): BinaryOperator() {
    override fun typecheck(l: FType<*>, r: FType<*>): FType<*> {
        val types = Pair(l, r)
        val operator = operatorByType[types] ?: throw TypeError("No operator found for types: $l, $r")
        return operator.typecheck(l, r)
    }

    override fun compute(l: FValue, r: FValue): FValue {
        val types = Pair(l.type, r.type)
        val operator = operatorByType[types] ?: throw RuntimeError("No operator found for types: $l, $r")
        return operator.compute(l, r)
    }
}