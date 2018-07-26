package io.github.spacialcircumstances.efun.interpreter

import io.github.spacialcircumstances.efun.car
import io.github.spacialcircumstances.efun.cdr
import io.github.spacialcircumstances.efun.expressions.AbstractExpression

interface IFunction {
    val parameterName: String
    val inType: FType<*>
    val outType: FType<*>
    fun run(arg: FValue, environment: InterpreterContext): FValue
}

class FunctionPointer(val function: IFunction, val environment: InterpreterContext) {
    fun run(arg: FValue): FValue {
        return function.run(arg, environment)
    }
}

class ValueFunction(override val parameterName: String, override val inType: FType<*>, override val outType: FType<*>, val expressions: List<AbstractExpression>) : IFunction {
    override fun run(arg: FValue, environment: InterpreterContext): FValue {
        if (arg.type != inType) throw IllegalStateException("Expected $inType as parameter: $parameterName, but got ${arg.type}")
        val result = expressions.map { it.evaluate(environment) }.last()
        if (outType != result.type) throw IllegalStateException("Expected $outType as result, but got ${result.type} instead")
        return result
    }
}

class CurryFunction(override val parameterName: String, override val inType: FType<*>, override val outType: FType<*>, val next: IFunction) : IFunction {
    override fun run(arg: FValue, environment: InterpreterContext): FValue {
        if (arg.type != inType) throw IllegalStateException("Expected $inType as parameter: $parameterName, but got ${arg.type}")
        val newEnv = environment.copy()
        newEnv[parameterName] = arg
        val fPointer = FunctionPointer(next, newEnv)
        return FValue(TFunction, fPointer)
    }
}

fun createFunction(parameters: List<Pair<String, FType<*>>>, body: List<AbstractExpression>, environment: InterpreterContext): FunctionPointer {
    val env = environment.copy()
    return if (parameters.size == 1) {
        val param = parameters.single()
        FunctionPointer(ValueFunction(param.first, param.second, TAny, body), env)
    } else {
        val next = createFunction(parameters.cdr(), body, env)
        val function = CurryFunction(parameters.car().first, parameters.car().second, TFunction, next.function)
        FunctionPointer(function, env)
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