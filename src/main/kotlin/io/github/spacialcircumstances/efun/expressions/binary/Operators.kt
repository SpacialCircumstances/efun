package io.github.spacialcircumstances.efun.expressions.binary

import io.github.spacialcircumstances.efun.interpreter.*

class BoolAndOperator: SimpleBinaryOperator<Boolean, Boolean, Boolean>(TBool, TBool, TBool) {
    override fun computeBinary(left: Boolean, right: Boolean): Boolean = left && right
}

class BoolOrOperator: SimpleBinaryOperator<Boolean, Boolean, Boolean>(TBool, TBool, TBool) {
    override fun computeBinary(left: Boolean, right: Boolean): Boolean = left || right
}

class BoolXorOperator: SimpleBinaryOperator<Boolean, Boolean, Boolean>(TBool, TBool, TBool) {
    override fun computeBinary(left: Boolean, right: Boolean): Boolean = left xor right
}

class AnyEqualOperator: BinaryOperator() {
    override fun typecheck(l: FType<*>, r: FType<*>): FType<*> {
        return TBool
    }

    override fun compute(l: FValue, r: FValue): FValue = FValue(TBool, (l.type == r.type) && (l.value == r.value))
}

class AnyNotEqualOperator: BinaryOperator() {
    override fun typecheck(l: FType<*>, r: FType<*>): FType<*> {
        return TBool
    }

    override fun compute(l: FValue, r: FValue): FValue = FValue(TBool, l.value != r.value)
}

class AdditionOperator: MultiOperator(mapOf(
        Pair(TInt, TInt) to IntAdditionOperator(),
        Pair(TFloat, TFloat) to FloatAdditionOperator()
))

class SubstractionOperator: MultiOperator(mapOf(
        Pair(TInt, TInt) to IntSubstractionOperator(),
        Pair(TFloat, TFloat) to FloatSubstractionOperator()
))

class MultiplicationOperator: MultiOperator(mapOf(
        Pair(TInt, TInt) to IntMultiplicationOperator(),
        Pair(TFloat, TFloat) to FloatMultiplicationOperator()
))

class DivisionOperator: MultiOperator(mapOf(
        Pair(TInt, TInt) to IntDivisionOperator(),
        Pair(TFloat, TFloat) to FloatDivisionOperator()
))

class GreaterEqualOperator: MultiOperator(mapOf(
        Pair(TInt, TInt) to IntGreaterEqualOperator(),
        Pair(TFloat, TFloat) to FloatGreaterEqualOperator()
))

class SmallerEqualOperator: MultiOperator(mapOf(
        Pair(TInt, TInt) to IntSmallerEqualOperator(),
        Pair(TFloat, TFloat) to FloatSmallerEqualOperator()
))

class GreaterOperator: MultiOperator(mapOf(
        Pair(TInt, TInt) to IntGreaterOperator(),
        Pair(TFloat, TFloat) to FloatGreaterOperator()
))

class SmallerOperator: MultiOperator(mapOf(
        Pair(TInt, TInt) to IntSmallerOperator(),
        Pair(TFloat, TFloat) to FloatSmallerOperator()
))