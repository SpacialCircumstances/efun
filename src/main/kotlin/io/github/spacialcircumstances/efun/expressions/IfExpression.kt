package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.TypeError
import io.github.spacialcircumstances.efun.interpreter.*

class IfExpression(private val condition: AbstractExpression, private val block: BlockExpression, private val elseBlock: BlockExpression): AbstractExpression() {
    override fun guessType(context: TypesContext): FType<*> {
        val condType = condition.guessType(context)
        if (condType != TBool) throw TypeError("Condition evaluation must return a boolean")
        val ifType = block.guessType(context)
        val elseType = elseBlock.guessType(context)
        if (ifType == elseType) {
            return ifType
        } else throw TypeError("Incompatible types: If expression returns ${ifType.name}, but else expression returns ${elseType?.name}")
    }

    override fun evaluate(context: InterpreterContext): FValue {
        val conditionResult = condition.evaluate(context)
        val isTrue = TBool.castValue(conditionResult)
        return if (isTrue) {
            val f = block.evaluate(context).value as FunctionPointer
            f.run(FValue(TVoid, null))
        } else {
            val f = elseBlock.evaluate(context).value as FunctionPointer
            f.run(FValue(TVoid, null))
        }
    }
}