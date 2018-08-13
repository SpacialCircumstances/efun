package io.github.spacialcircumstances.efun.interpreter

import io.github.spacialcircumstances.efun.car
import io.github.spacialcircumstances.efun.cdr
import io.github.spacialcircumstances.efun.expressions.AbstractExpression

interface IFunction {
    val parameterName: String
    val type: FunctionType
    fun run(arg: FValue, environment: InterpreterContext): FValue
}

class FunctionPointer(val function: IFunction, val environment: InterpreterContext) {
    fun run(arg: FValue): FValue {
        return function.run(arg, environment.copy())
    }
}

class ValueFunction(override val parameterName: String, override val type: FunctionType, val expressions: List<AbstractExpression>) : IFunction {
    override fun run(arg: FValue, environment: InterpreterContext): FValue {
        environment[parameterName] = arg
        return expressions.map { it.evaluate(environment) }.last()
    }
}

class CurryFunction(override val parameterName: String, override val type: FunctionType, val next: IFunction) : IFunction {
    override fun run(arg: FValue, environment: InterpreterContext): FValue {
        val newEnv = environment.copy()
        newEnv[parameterName] = arg
        val fPointer = FunctionPointer(next, newEnv)
        return FValue(TFunction, fPointer)
    }
}

class EmptyFunction(val expressions: List<AbstractExpression>, val outType: FType<*>): IFunction {
    override val parameterName: String
        get() = ""
    override val type = FunctionType(TVoid, outType)

    override fun run(arg: FValue, environment: InterpreterContext): FValue {
       return expressions.map { it.evaluate(environment) }.last()
    }
}

tailrec fun createFunctionType(parameters: List<FType<*>>, returnType: FType<*>): FunctionType {
    if (parameters.isEmpty()) return FunctionType(TVoid, returnType)
    val lastType = FunctionType(parameters.last(), returnType)
    if (parameters.size == 1) return lastType
    val rest = parameters.take(parameters.size - 1)
    return createFunctionType(rest, lastType)
}

fun createFunction(parameterNames: List<String>, type: FunctionType, body: List<AbstractExpression>, environment: InterpreterContext): FunctionPointer {
    return if (type.inType == TVoid) {
        FunctionPointer(EmptyFunction(body, type.outType), environment)
    } else {
        if (type.outType !is FunctionType || parameterNames.size == 1) {
            FunctionPointer(ValueFunction(parameterNames.car(), type, body), environment)
        } else {
            val next = createFunction(parameterNames.cdr(), type.outType, body, environment)
            val function = CurryFunction(parameterNames.car(), type, next.function)
            FunctionPointer(function, environment)
        }
    }
}

fun runWhileFunction(fp: FunctionPointer, values: List<FValue>): FValue {
    var currentFp = fp
    var res: FValue? = null
    for (i in 0 until values.size) {
        val result = currentFp.run(values[i])
        if (result.type == TFunction) {
            currentFp = TFunction.castValue(result)
            res = FValue(TFunction, currentFp)
        } else if (i == values.size - 1) {
            res = result
            break
        } else throw IllegalStateException("Expected return value of function, but got ${result.type}")
    }
    return res ?: throw IllegalStateException()
}