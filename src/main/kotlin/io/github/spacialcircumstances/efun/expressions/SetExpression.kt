package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.TypeError
import io.github.spacialcircumstances.efun.interpreter.*

class SetExpression(private val expression: AbstractExpression, private val name: String): AbstractExpression() {
    private val keys = name.split('.')

    override fun guessType(context: TypesContext): FType<*> {
        val exprType = expression.guessType(context)
        val varType = getType(context, keys) ?: throw TypeError("Variable $name not found")
        if (exprType != varType) {
            throw TypeError("Expression type of ${exprType.name} does not match variable type of ${varType.name}")
        }
        return TVoid
    }

    override fun evaluate(context: InterpreterContext): FValue {
        val value = expression.evaluate(context)
        context.setMutable(name, value) //TODO
        return FValue(TVoid, null)
    }
}