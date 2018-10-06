package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

class ModuleExpression(val name: String, val expressions: List<AbstractExpression>): AbstractExpression() {
    private var moduleType: ModuleType? = null

    override fun evaluate(context: InterpreterContext): FValue {
        val moduleContext = InterpreterContext(null)
        expressions.forEach { it.evaluate(moduleContext) }
        val moduleInstance = ModuleInstance(moduleContext)
        val value = FValue(moduleType!!, moduleInstance)
        context[name] = value
        return value
    }

    override fun guessType(context: TypeContext): FType<*> {
        val moduleContext = TypeContext(null, context.additionalTypeMappings)
        expressions.forEach { it.guessType(moduleContext) }
        val module = Module(moduleContext)
        val type = ModuleType(name, module)
        context.registerChildModule(name, module)
        context[name] = type
        context.typesResolveContext.importChildModule(name, module)
        moduleType = type
        return type
    }
}