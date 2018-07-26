package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.interpreter.*

class IfExpression(val condition: AbstractExpression, val block: BlockExpression, val elseBlock: BlockExpression?): AbstractExpression() {
    override fun evaluate(context: InterpreterContext): FValue {
        val conditionResult = condition.evaluate(context)
        if (conditionResult.type != TBool) throw IllegalStateException("If expression takes a expression evaluating to Bool as condition")
        val isTrue = TBool.castValue(conditionResult)
        return if (isTrue) {
            val f = block.evaluate(context).value as FunctionPointer
            f.run(FValue(TVoid, null))
        } else {
            if (elseBlock != null) {
                val f = elseBlock.evaluate(context).value as FunctionPointer
                f.run(FValue(TVoid, null))
            } else FValue(TVoid, null)
        }
    }
}