package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.RuntimeError
import io.github.spacialcircumstances.efun.TypeError
import io.github.spacialcircumstances.efun.interpreter.*

class ModuleExpression(val name: String, val uses: List<String>, val expressions: List<AbstractExpression>): AbstractExpression() {
    private var moduleType: ModuleType? = null

    override fun evaluate(context: InterpreterContext): FValue {
        val moduleContext = InterpreterContext(null)
        uses.forEach {
            moduleContext.importExternModule(it, context[it] ?: throw RuntimeError("Module $it not found"))
        }
        expressions.forEach { it.evaluate(moduleContext) }
        val moduleInstance = ModuleInstance(moduleContext)
        val value = FValue(moduleType!!, moduleInstance)
        context[name] = value
        return value
    }

    override fun guessType(context: TypesContext): FType<*> {
        val moduleContext = TypesContext(null, context.defaultTypeMappings)
        uses.forEach {
            val module = context.getType(it) as? ModuleType ?: throw TypeError("Module $it not found in parent context")
            moduleContext.importExternModule(module)
        }
        expressions.forEach { it.guessType(moduleContext) }
        val module = Module(moduleContext)
        val type = ModuleType(name, module)
        context.importChildModule(type)
        moduleType = type
        return type
    }
}