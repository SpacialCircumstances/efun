package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

class ModuleExpression(val name: String, val uses: List<String>, val expressions: List<AbstractExpression>): AbstractTypeExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        TODO()
    }

    override fun type(context: TypesContext): DataStructureType {
        TODO()
    }
}