package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

class ModuleExpression(val name: String, val expressions: List<AbstractExpression>): AbstractExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun guessType(context: TypeContext): FType<*> {
        val moduleContext = TypeContext(null, context.additionalTypeMappings)
        expressions.forEach { it.guessType(moduleContext) }
        //val module = ModuleType()
        return TVoid
    }
}