package io.github.spacialcircumstances.efun.expressions.binary

import io.github.spacialcircumstances.efun.interpreter.TBool
import io.github.spacialcircumstances.efun.interpreter.TFloat

class FloatAdditionOperator: SimpleBinaryOperator<Double, Double, Double>(TFloat, TFloat, TFloat) {
    override fun computeBinary(left: Double, right: Double): Double = left + right
}

class FloatSubstractionOperator: SimpleBinaryOperator<Double, Double, Double>(TFloat, TFloat, TFloat) {
    override fun computeBinary(left: Double, right: Double): Double = left - right
}

class FloatMultiplicationOperator: SimpleBinaryOperator<Double, Double, Double>(TFloat, TFloat, TFloat) {
    override fun computeBinary(left: Double, right: Double): Double = left * right
}

class FloatDivisionOperator: SimpleBinaryOperator<Double, Double, Double>(TFloat, TFloat, TFloat) {
    override fun computeBinary(left: Double, right: Double): Double = left / right
}

class FloatGreaterEqualOperator: SimpleBinaryOperator<Double, Double, Boolean>(TFloat, TFloat, TBool) {
    override fun computeBinary(left: Double, right: Double): Boolean = left >= right
}

class FloatSmallerEqualOperator: SimpleBinaryOperator<Double, Double, Boolean>(TFloat, TFloat, TBool) {
    override fun computeBinary(left: Double, right: Double): Boolean = left <= right
}

class FloatSmallerOperator: SimpleBinaryOperator<Double, Double, Boolean>(TFloat, TFloat, TBool) {
    override fun computeBinary(left: Double, right: Double): Boolean = left < right
}
class FloatGreaterOperator: SimpleBinaryOperator<Double, Double, Boolean>(TFloat, TFloat, TBool) {
    override fun computeBinary(left: Double, right: Double): Boolean = left > right
}
