package io.github.spacialcircumstances.efun.interpreter

import io.github.spacialcircumstances.efun.RuntimeError
import io.github.spacialcircumstances.efun.car
import io.github.spacialcircumstances.efun.cdr
import io.github.spacialcircumstances.efun.expressions.AbstractExpression

interface IFunction {
    val type: FunctionType
    fun run(arg: FValue, environment: InterpreterContext): FValue
}

interface IFunctionPointer {
    fun runWithArguments(values: List<FValue>): FValue
}

class FunctionPointer(val function: IFunction, val environment: InterpreterContext) : IFunctionPointer {
    fun run(arg: FValue): FValue {
        return function.run(arg, environment.copy())
    }

    override fun runWithArguments(values: List<FValue>): FValue {
        var currentFp = this
        var res: FValue? = null
        for (i in 0 until values.size) {
            val result = currentFp.run(values[i])
            if (result.type is FunctionType) {
                currentFp = result.type.castValue(result) as FunctionPointer
                res = FValue(currentFp.function.type, currentFp)
            } else if (i == values.size - 1) {
                res = result
                break
            } else throw RuntimeError("Expected return value of function, but got ${result.type}")
        }
        return res ?: throw RuntimeError("Reached illegal interpreter state")
    }
}

class ValueFunction(val parameterName: String, override val type: FunctionType, val expressions: List<AbstractExpression>) : IFunction {
    override fun run(arg: FValue, environment: InterpreterContext): FValue {
        environment[parameterName] = arg
        return expressions.map { it.evaluate(environment) }.last()
    }
}

class CurryFunction(val parameterName: String, override val type: FunctionType, val next: IFunction) : IFunction {
    override fun run(arg: FValue, environment: InterpreterContext): FValue {
        val newEnv = environment.copy()
        newEnv[parameterName] = arg
        val fPointer = FunctionPointer(next, newEnv)
        return FValue(next.type, fPointer)
    }
}

class EmptyFunction(val expressions: List<AbstractExpression>, val outType: FType<*>) : IFunction {
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