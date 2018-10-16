package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.DataStructureType
import io.github.spacialcircumstances.efun.interpreter.FValue
import io.github.spacialcircumstances.efun.interpreter.InterpreterContext
import io.github.spacialcircumstances.efun.interpreter.TypesContext

abstract class AbstractTypeExpression {
    abstract fun evaluate(context: InterpreterContext)
    abstract fun type(context: TypesContext): DataStructureType
}