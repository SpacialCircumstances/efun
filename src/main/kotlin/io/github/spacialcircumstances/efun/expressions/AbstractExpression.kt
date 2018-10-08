package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.FType
import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.InterpreterContext
import io.github.spacialcircumstances.efun.interpreter.TypesContext

abstract class AbstractExpression {
    abstract fun evaluate(context: InterpreterContext): FValue
    abstract fun guessType(context: TypesContext): FType<*>
}