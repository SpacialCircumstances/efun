package io.github.spacialcircumstances.efun.interpreter

import io.github.spacialcircumstances.efun.RuntimeError

interface IFunctionPointer {
    fun runWithArguments(values: List<FValue>): FValue
}

class ExternalCurryFunctionPointer(val externalFunction: ExternalFunction, val args: List<FValue>): IFunctionPointer {
    override fun runWithArguments(values: List<FValue>): FValue {
        val newArgs = args + values
        return if (newArgs.size == externalFunction.argumentCount) {
            externalFunction.run(newArgs)
        } else {
            val resultType = getReturnType(externalFunction.functionType, newArgs.size)!!
            FValue(resultType, ExternalCurryFunctionPointer(externalFunction, args))
        }
    }
}

class ExternalFunctionPointer(val externalFunction: ExternalFunction): IFunctionPointer {
    override fun runWithArguments(values: List<FValue>): FValue {
        return ExternalCurryFunctionPointer(externalFunction, values).runWithArguments(emptyList())
    }
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

