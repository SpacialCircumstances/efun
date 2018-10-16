package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

class ModuleExpression(val name: String, private val uses: List<String>, val expressions: List<AbstractExpression>): AbstractTypeExpression() {
    var type: DataStructureType? = null

    override fun evaluate(context: InterpreterContext) {
        val moduleContext = InterpreterContext(null)
        uses.forEach {
            moduleContext.importExternModule(it, context[it]!!)
        }
        expressions.forEach {
            it.evaluate(moduleContext)
        }
        val instance = DataStructureInstance(moduleContext)
        val value = FValue(type!!, instance)
        context[name] = value
    }

    override fun type(context: TypesContext): DataStructureType {
        val moduleContext = TypesContext(null, context.defaultTypeMappings)
        uses.forEach {
            val mod = (context.getType(it) as TypeType).type as DataStructureType
            moduleContext.importExternModule(mod)
        }
        expressions.forEach {
            it.guessType(moduleContext)
        }
        val dataStructureDefinition = DataStructureDefinition(moduleContext)
        type = DataStructureType(name, dataStructureDefinition)
        return type!!
    }
}