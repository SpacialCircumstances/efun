package io.github.spacialcircumstances.efun.expressions

import io.github.spacialcircumstances.efun.RuntimeError
import io.github.spacialcircumstances.efun.TypeError
import io.github.spacialcircumstances.efun.car
import io.github.spacialcircumstances.efun.cdr
import io.github.spacialcircumstances.efun.interpreter.*

class VariableExpression(name: String): AbstractExpression() {
    private val keys = name.split('.')

    override fun guessType(context: TypesContext): FType<*> {
        return getType(context, keys) ?: throw TypeError("Variable ${keys.joinToString(".")} does not exist in this scope")
    }

    override fun evaluate(context: InterpreterContext): FValue {
        return getValue(context, keys) ?: throw RuntimeError("Value ${keys.joinToString(".")} does not exist in this scope")
    }
}

fun getValue(start: IFValueStore, keys: List<String>): FValue? {
    return if (keys.size == 1) {
        start[keys.single()]
    } else {
        val next = start[keys.car()] ?: return null
        getValue(next.value as IFValueStore, keys.cdr())
    }
}

fun getType(start: IFTypeStore, keys: List<String>): FType<*>? {
    return if (keys.size == 1) {
        start.getType(keys.single())
    } else {
        val next = start.getType(keys.car())?.subTypeStore ?: return null
        getType(next, keys.cdr())
    }
}