package io.github.spacialcircumstances.efun.expressions.binary

import io.github.spacialcircumstances.efun.interpreter.TBool
import io.github.spacialcircumstances.efun.interpreter.TInt

class IntAdditionOperator: SimpleBinaryOperator<Long, Long, Long>(TInt, TInt, TInt) {
    override fun computeBinary(left: Long, right: Long): Long = left + right
}

class IntSubstractionOperator: SimpleBinaryOperator<Long, Long, Long>(TInt, TInt, TInt) {
    override fun computeBinary(left: Long, right: Long): Long = left - right
}

class IntMultiplicationOperator: SimpleBinaryOperator<Long, Long, Long>(TInt, TInt, TInt) {
    override fun computeBinary(left: Long, right: Long): Long = left * right
}

class IntDivisionOperator: SimpleBinaryOperator<Long, Long, Long>(TInt, TInt, TInt) {
    override fun computeBinary(left: Long, right: Long): Long = left / right
}

class IntGreaterEqualOperator: SimpleBinaryOperator<Long, Long, Boolean>(TInt, TInt, TBool) {
    override fun computeBinary(left: Long, right: Long): Boolean = left >= right
}

class IntSmallerEqualOperator: SimpleBinaryOperator<Long, Long, Boolean>(TInt, TInt, TBool) {
    override fun computeBinary(left: Long, right: Long): Boolean = left <= right
}

class IntSmallerOperator: SimpleBinaryOperator<Long, Long, Boolean>(TInt, TInt, TBool) {
    override fun computeBinary(left: Long, right: Long): Boolean = left < right
}
class IntGreaterOperator: SimpleBinaryOperator<Long, Long, Boolean>(TInt, TInt, TBool) {
    override fun computeBinary(left: Long, right: Long): Boolean = left > right
}
