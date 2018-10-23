package io.github.spacialcircumstances.efun.interpreter

import io.github.spacialcircumstances.efun.car
import io.github.spacialcircumstances.efun.carOrNull
import io.github.spacialcircumstances.efun.cdr
import io.github.spacialcircumstances.efun.expressions.AbstractExpression

//Describes efun-native functions
interface IFunction {
    val type: FunctionType
    fun run(arg: FValue, environment: InterpreterContext): FValue
}

class ValueFunction(private val parameterName: String, override val type: FunctionType, val expressions: List<AbstractExpression>) : IFunction {
    override fun run(arg: FValue, environment: InterpreterContext): FValue {
        environment[parameterName] = arg
        return expressions.asSequence().map { it.evaluate(environment) }.last()
    }
}

class CurryFunction(private val parameterName: String, override val type: FunctionType, val next: IFunction) : IFunction {
    override fun run(arg: FValue, environment: InterpreterContext): FValue {
        val newEnv = InterpreterContext(environment)
        newEnv[parameterName] = arg
        val fPointer = FunctionPointer(next, newEnv)
        return FValue(next.type, fPointer)
    }
}

class EmptyFunction(val expressions: List<AbstractExpression>, outType: FType<*>) : IFunction {
    override val type = FunctionType(TVoid, outType)

    override fun run(arg: FValue, environment: InterpreterContext): FValue {
        return expressions.asSequence().map { it.evaluate(environment) }.last()
    }
}

class ConstructorFunction(private val parameterName: String?, override val type: FunctionType, private val dataStructureType: DataStructureType, private val body: List<AbstractExpression>) : IFunction {
    override fun run(arg: FValue, environment: InterpreterContext): FValue {
        parameterName?.let {
            environment[it] = arg
        }
        body.forEach { it.evaluate(environment) }
        val instance = DataStructureInstance(dataStructureType.definition, environment)
        return FValue(dataStructureType, instance)
    }
}

fun createConstructor(parameterNames: List<String>, type: FunctionType, dataStructureType: DataStructureType, environment: InterpreterContext, body: List<AbstractExpression>): FunctionPointer {
    return if (type.outType !is FunctionType || parameterNames.size == 1) {
        FunctionPointer(ConstructorFunction(parameterNames.carOrNull(), type, dataStructureType, body), environment)
    } else {
        val next = createConstructor(parameterNames.cdr(), type.outType, dataStructureType, environment, body)
        val function = CurryFunction(parameterNames.car(), type, next.function)
        FunctionPointer(function, environment)
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